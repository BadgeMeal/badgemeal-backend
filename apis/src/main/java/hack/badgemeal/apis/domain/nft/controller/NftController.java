package hack.badgemeal.apis.domain.nft.controller;

import hack.badgemeal.apis.domain.nft.model.*;
import hack.badgemeal.apis.domain.nft.service.NftService;
import hack.badgemeal.apis.domain.ocr.model.MintData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"NFT API"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api")
public class NftController {
    private final NftService nftService;

    @ApiOperation(value = "해당 주소의 현재 회차의 NFT 발급 횟수를 조회합니다.")
    @GetMapping("/nft/mintCount")
    public int getNftMintCount(NftMintCountGetRequestParam params) {
        return nftService.nftCount(params);
    }

    @ApiOperation(value = "해당 주소의 현재 회차의 NFT 발급 횟수를 수정합니다.")
    @PutMapping("/nft/mintCount")
    public NftMintCount putNftMintCount(NftMintCountPutRequestParam params) {
        return nftService.nftCount(params);
    }

    @ApiImplicitParam(name = "address", dataType = "String", defaultValue = "0x0000000000000000000000000000000000000000", example = "0x0000000000000000000000000000000000000000")
    @ApiOperation(value = "해당 주소에 매핑된 Mint Data를 조회합니다.")
    @GetMapping("/mintData")
    public MintDataResponse getMintData(@RequestParam("address") String address) {
        return nftService.mintData(address);
    }

    @ApiOperation(value = "해당 주소에 매핑할 Mint Data를 저장합니다.")
    @PostMapping("/mintData")
    public MintData getMintData(MintDataPostRequestParam params) {
        return nftService.postMintData(params);
    }

    @ApiImplicitParam(name = "address", dataType = "String", defaultValue = "0x0000000000000000000000000000000000000000", example = "0x0000000000000000000000000000000000000000")
    @ApiOperation(value = "해당 주소에 매핑된 Mint Data를 초기화합니다.")
    @GetMapping("/mintData/init")
    public boolean initMintData(@RequestParam("address") String address) {
        return nftService.initMintData(address);
    }
}
