package hack.badgemeal.apis.domain.ocr.service;

import com.klaytn.caver.Caver;
import com.klaytn.caver.abi.datatypes.Type;
import com.klaytn.caver.contract.Contract;
import hack.badgemeal.apis.common.constant.BadgemealConstant;
import hack.badgemeal.apis.common.dto.ReceiptDto;
import hack.badgemeal.apis.common.enums.ErrorCode;
import hack.badgemeal.apis.common.enums.ResponseStatus;
import hack.badgemeal.apis.common.exceptions.CustomException;
import hack.badgemeal.apis.common.response.Message;
import hack.badgemeal.apis.domain.draw.model.Round;
import hack.badgemeal.apis.domain.draw.repository.RoundRepository;
import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.repository.MenuRepository;
import hack.badgemeal.apis.domain.ocr.model.MetadataResponse;
import hack.badgemeal.apis.domain.ocr.model.MintData;
import hack.badgemeal.apis.domain.ocr.model.OcrResponse;
import hack.badgemeal.apis.domain.ocr.model.OcrResult;
import hack.badgemeal.apis.domain.ocr.repository.MintDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.web3j.protocol.http.HttpService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OcrService {
    @Value("${badgemeal.kakao-app-key}")
    private String APP_KEY;
    @Value("${badgemeal.caver.access-key-id}")
    private String accessKeyId;
    @Value("${badgemeal.caver.secret-access-key}")
    private String secretAccessKey;
    @Value("${badgemeal.caver.chain-id}")
    private String chainId;
    @Value("${badgemeal.caver.nft-contract-address}")
    private String nftContractAddress;
    private final MenuRepository menuRepository;
    private final MintDataRepository mintDataRepository;
    private final RoundRepository roundRepository;

    public ResponseEntity<Message> ocrVisionText(MultipartFile image, ReceiptDto receiptDto) {
        StringBuilder ext = new StringBuilder();
        OcrResponse response = null;
        String fileFullPath = null;

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
                int[] pixels = new int[resizeWidth * resizeHeight];
                PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, resizeWidth, resizeHeight, pixels, 0, resizeWidth);
                pg.grabPixels();

                resizedImage = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_INT_RGB);
                resizedImage.setRGB(0, 0,  resizeWidth, resizeHeight, pixels, 0, resizeWidth);
            }

            if (resizedImage != null) ocrImage = resizedImage;
            else ocrImage = in;

            String fileName = "ocrImage_" + UUID.randomUUID();
            fileFullPath = tempFilePath + File.separator + fileName + "." + ext;

            ImageIO.write(ocrImage,
                    ext.toString(),
                    new File(fileFullPath));

            WebClient webClient = WebClient.builder()
                    .baseUrl("https://dapi.kakao.com/v2")
                    .build();

            response = webClient.post()
                    .uri("/vision/text/ocr")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header("authorization", "KakaoAK " + APP_KEY)
                    .body(BodyInserters.fromMultipartData(fromFileImage(new File(fileFullPath))))
                    .retrieve()
                    .bodyToMono(OcrResponse.class)
                    .block();

        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            new ResponseEntity<>(
                    Message.builder().status(ResponseStatus.FAILED).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // OCR 인식 결과에서 메뉴 키워드 검색
        Menu menu = menuRepository.findById(receiptDto.getMenuNo())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        boolean isFindKeyword = false;
        String keyword = menu.getKeyword();

        try {
            for (OcrResult result : Objects.requireNonNull(response).getResult()) {
                if (isFindKeyword) break;
                ArrayList<String> recogWords = result.getRecognition_words();

                for (int i = 0; i < recogWords.size(); i++) {
                    if (recogWords.get(i).toUpperCase().contains(keyword)) {
                        isFindKeyword = true;
                        break;
                    }
                }
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
            new ResponseEntity<>(
                    Message.builder().status(ResponseStatus.FAILED).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (isFindKeyword) {
            int randomTokenId = 0;
            MetadataResponse metadataResponse = uploadReceiptMetadataKasApi(fileFullPath);
            try {
                randomTokenId = getValidTokenId();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new CustomException(ErrorCode.CAVER_OWNEROF_CALL);
            }
            Round nowRound = roundRepository.findByIsNowIsNotNull();
            if (!mintDataRepository.existsByAddressAndRound(receiptDto.getAddress(), nowRound.getRound())) {
                MintData mintData = new MintData(receiptDto.getAddress(), nowRound.getRound(), randomTokenId, metadataResponse.getUri());
                mintDataRepository.save(mintData);
            } else {
                throw new CustomException(ErrorCode.MINT_DATA_EXIST);
            }

            return new ResponseEntity<>(
                    Message.builder().status(ResponseStatus.SUCCESS)
                            .data(Map.of("tokenId", randomTokenId, "metadataUri", metadataResponse.getUri()))
                            .build(),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                Message.builder().status(ResponseStatus.FAILED).build(),
                HttpStatus.OK);
    }

    private static File convert(MultipartFile file, StringBuilder ext) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = convFile.getName();
        ext.append(convFile.getName().substring(fileName.lastIndexOf(".") + 1));

        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }

    public MultiValueMap<String, HttpEntity<?>> fromFileImage(File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(file));
        return builder.build();
    }

    public MultiValueMap<String, HttpEntity<?>> fromFile(File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(file));
        return builder.build();
    }

    private int getValidTokenId() throws Exception {
        HttpService httpService = new HttpService("https://node-api.klaytnapi.com/v1/klaytn");
        httpService.addHeader("Authorization", Credentials.basic(accessKeyId, secretAccessKey));
        httpService.addHeader("x-chain-id", chainId);
        Caver caver = new Caver(httpService);

        URL resource = OcrService.class.getResource("/abi/MintBadgemealNFT_abi.json");
        String abi = readFileAsString(resource.toURI());
        Contract nftContract = caver.contract.create(abi, nftContractAddress);

        boolean isTokenIdUniq = false;
        int randomTokenID = 0;
        int loopMaxCount = 0;
        while (!isTokenIdUniq && loopMaxCount < 100) {
            try {
                randomTokenID = (int) Math.floor(Math.random() * Integer.MAX_VALUE);
                List<Type> callResult = nftContract.call("ownerOf", randomTokenID);

                if (callResult.isEmpty()) {
                    isTokenIdUniq = true;
                }
            } catch (IOException e) {
                if (!e.getMessage().contains("evm")) {
                    throw new CustomException(ErrorCode.CAVER_OWNEROF_CALL);
                } else {
                    isTokenIdUniq = true;
                }
            }
            loopMaxCount++;
        }

        return randomTokenID;
    }

    private String readFileAsString(URI uri) throws Exception {
        return new String(Files.readAllBytes(Paths.get(uri)));
    }

    private MetadataResponse uploadReceiptMetadataKasApi(String fileFullPath) {
        File imageFile = new File(fileFullPath);

        WebClient webClient = WebClient.builder()
                .baseUrl("https://metadata-api.klaytnapi.com/v1")
                .build();

        MetadataResponse metadataResponse = webClient.post()
                .uri("/metadata/asset")
                .header("x-chain-id", chainId)
                .header("authorization", Credentials.basic(accessKeyId, secretAccessKey))
                .body(BodyInserters.fromMultipartData(fromFile(imageFile)))
                .retrieve()
                .bodyToMono(MetadataResponse.class)
                .block();

        if (metadataResponse == null) {
            throw new CustomException(ErrorCode.KAS_METADATA_API);
        }

        return metadataResponse;
    }
}
