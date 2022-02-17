package hack.badgemeal.apis.domain.ocr.service;

import hack.badgemeal.apis.common.constant.BadgemealConstant;
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
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OcrService {
    @Value("${badgemeal.kakao-app-key}")
    private String APP_KEY;
    @Value("classpath:tmp")
    private String filePath;

    public String ocrVisionText(MultipartFile image) {
        StringBuilder ext = new StringBuilder();
//        String filePath = File.separator + "resources" + File.separator;
        try {
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
                    new File("C:\\Users\\ChoiSY\\Documents\\" + fileName + "." + ext));

            WebClient webClient = WebClient.builder()
                    .baseUrl("https://dapi.kakao.com/v2")
                    .build();

            return webClient.post()
                    .uri("/vision/text/ocr")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header("authorization", "KakaoAK " + APP_KEY)
                    .body(BodyInserters.fromMultipartData(fromFile(new File("C:\\Users\\ChoiSY\\Documents\\" + fileName + "." + ext))))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "";
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            return "";
        }
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
}
