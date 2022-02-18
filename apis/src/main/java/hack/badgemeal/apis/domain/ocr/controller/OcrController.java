package hack.badgemeal.apis.domain.ocr.controller;

import hack.badgemeal.apis.common.dto.ReceiptDto;
import hack.badgemeal.apis.common.enums.ResponseStatus;
import hack.badgemeal.apis.domain.ocr.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class OcrController {
    private final OcrService ocrService;

    @PostMapping("/api/verify/receipt")
    public ResponseStatus postOcrVisionText(@RequestParam("img") MultipartFile image, ReceiptDto receiptDto) {
        return ocrService.ocrVisionText(image, receiptDto);
    }
}