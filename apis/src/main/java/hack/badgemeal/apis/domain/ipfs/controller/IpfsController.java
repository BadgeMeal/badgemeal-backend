package hack.badgemeal.apis.domain.ipfs.controller;

import hack.badgemeal.apis.common.dto.ResultDto;
import hack.badgemeal.apis.domain.ipfs.service.IpfsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = {"IPFS에 MasterNFT 이미지를 업로드하고 DB에 저장해 관리하는 Controller"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("ipfs")
public class IpfsController {
    private final IpfsService ipfsService;

    @PostMapping("/uploadMasterNftMetadata")
    public Object postIpfsImgUpload(@RequestParam("menu_no") Long menu_no, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("img") MultipartFile image){
        ResultDto resultDto = new ResultDto();

        String status = "fail";
        try{
            resultDto = ipfsService.uploadToIPFS(menu_no, title, description, image);
        }catch (Exception e){
            e.printStackTrace();
            status =  "fail";
            resultDto.setMsg(e.getMessage());
        }

        resultDto.setStatus(status);
        return resultDto;
    }

    @GetMapping("/getMasterNftMetadata")
    public Object postIpfsImgUpload(@RequestParam("menu_no") Long menu_no){
         return ipfsService.getIpfsUrlByMenuNo(menu_no);
    }
}
