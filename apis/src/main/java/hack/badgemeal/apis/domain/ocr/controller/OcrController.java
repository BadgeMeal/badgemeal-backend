package hack.badgemeal.apis.domain.ocr.controller;

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

    @PostMapping("/api/ocr/receipt")
    public String postOcrVisionText(@RequestParam("img") MultipartFile image) {
        return ocrService.ocrVisionText(image);
    }
}
