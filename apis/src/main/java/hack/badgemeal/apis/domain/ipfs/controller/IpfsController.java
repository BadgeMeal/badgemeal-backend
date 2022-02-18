package hack.badgemeal.apis.domain.ipfs.controller;

import hack.badgemeal.apis.domain.ipfs.service.IpfsService;
import hack.badgemeal.apis.domain.ocr.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class IpfsController {
    private final IpfsService ipfsService;

    @PostMapping("/api/ipfs/upload")
    public String postIpfsImgUpload(@RequestParam("img") MultipartFile image) throws IOException {
        return ipfsService.uploadToIPFS(image);
    }


    @GetMapping("/api/ipfs/download")
    public String postIpfsImgUpload(@RequestParam("hash") String multihash) throws IOException {
        return ipfsService.downloadFromIPFS(multihash);
    }
}
