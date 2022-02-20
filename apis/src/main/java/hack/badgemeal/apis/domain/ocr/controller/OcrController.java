package hack.badgemeal.apis.domain.ocr.controller;

import hack.badgemeal.apis.common.response.Message;
import hack.badgemeal.apis.domain.ocr.model.VerifyReceiptRequestParam;
import hack.badgemeal.apis.domain.ocr.service.OcrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"메뉴 인증 API"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class OcrController {
    private final OcrService ocrService;

    @ApiResponses({
            @ApiResponse(code = 500,
                    message = "첨부된 파일이 없을 때, 해당 주소에 매핑된 뽑기 결과가 없을 때, 메타데이터 업로드 API 실패했을 때, OCR 인증 API 실패했을 때"),
    })
    @ApiOperation(value = "영수증 사진을 인증합니다.")
    @PostMapping("/api/verify/receipt")
    public ResponseEntity<Message> postOcrVisionText(VerifyReceiptRequestParam params) {
        return ocrService.ocrVisionText(params);
    }
}
