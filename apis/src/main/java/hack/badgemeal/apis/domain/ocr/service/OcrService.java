package hack.badgemeal.apis.domain.ocr.service;

import hack.badgemeal.apis.common.constant.BadgemealConstant;
import hack.badgemeal.apis.common.dto.ReceiptDto;
import hack.badgemeal.apis.common.enums.ErrorCode;
import hack.badgemeal.apis.common.enums.ResponseStatus;
import hack.badgemeal.apis.common.exceptions.CustomException;
import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.repository.MenuRepository;
import hack.badgemeal.apis.domain.ocr.model.OcrResponse;
import hack.badgemeal.apis.domain.ocr.model.OcrResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OcrService {
    @Value("${badgemeal.kakao-app-key}")
    private String APP_KEY;
    private final MenuRepository menuRepository;

    public ResponseStatus ocrVisionText(MultipartFile image, ReceiptDto receiptDto) {
        StringBuilder ext = new StringBuilder();
        OcrResponse response = null;

        try {
            Path temp = Files.createTempFile("", ".tmp");
            String absolutePath = temp.toString();
            String separator = FileSystems.getDefault().getSeparator();
            String tempFilePath = absolutePath
                    .substring(0, absolutePath.lastIndexOf(separator));

            BufferedImage in = ImageIO.read(convert(image, ext));
            BufferedImage resizedImage = null;
            BufferedImage ocrImage;
            int originWidth = in.getWidth();
            int originHeight = in.getHeight();

            // ocr api pixel 제약사항 초과시 resize
            if (originWidth > BadgemealConstant.LIMIT_PX || originHeight > BadgemealConstant.LIMIT_PX) {
                float ratio = (float) BadgemealConstant.LIMIT_PX / Math.max(originWidth, originHeight);
                int resizeWidth = (int) Math.floor(ratio * originWidth);
                int resizeHeight = (int) Math.floor(ratio * originHeight);

                Image imgTarget = in.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_SMOOTH);
                int pixels[] = new int[resizeWidth * resizeHeight];
                PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, resizeWidth, resizeHeight, pixels, 0, resizeWidth);
                pg.grabPixels();

                resizedImage = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_INT_RGB);
                resizedImage.setRGB(0, 0,  resizeWidth, resizeHeight, pixels, 0, resizeWidth);
            }

            if (resizedImage != null) ocrImage = resizedImage;
            else ocrImage = in;

            String fileName = "ocrImage_" + UUID.randomUUID();
            ImageIO.write(ocrImage,
                    ext.toString(),
                    new File(tempFilePath + fileName + "." + ext));

            WebClient webClient = WebClient.builder()
                    .baseUrl("https://dapi.kakao.com/v2")
                    .build();

            response = webClient.post()
                    .uri("/vision/text/ocr")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header("authorization", "KakaoAK " + APP_KEY)
                    .body(BodyInserters.fromMultipartData(fromFile(new File(tempFilePath + fileName + "." + ext))))
                    .retrieve()
                    .bodyToMono(OcrResponse.class)
                    .block();

        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return ResponseStatus.FAILED;
        }

        // OCR 인식 결과에서 메뉴 키워드 검색
        Menu menu = menuRepository.findById(receiptDto.getMenuNo())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        String keyword = menu.getKeyword();

        for (OcrResult result : response.getResult()) {
            ArrayList<String> recogWords = result.getRecognition_words();

            for (int i = 0; i < recogWords.size(); i++) {
                if (recogWords.get(i).toUpperCase().contains(keyword)) {
                    return ResponseStatus.SUCCESS;
                }
            }
        }

        return ResponseStatus.FAILED;
    }

    private static File convert(MultipartFile file, StringBuilder ext) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        String fileName = convFile.getName();
        ext.append(convFile.getName().substring(fileName.lastIndexOf(".") + 1));

        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }

    public MultiValueMap<String, HttpEntity<?>> fromFile(File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(file));
        return builder.build();
    }

//    private static boolean findKeyword(Map<String, Object>)
}
