package hack.badgemeal.apis.domain.nft.controller;

import hack.badgemeal.apis.domain.nft.model.NftMintCount;
import hack.badgemeal.apis.domain.nft.model.NftMintCountGetRequestParam;
import hack.badgemeal.apis.domain.nft.model.NftMintCountPutRequestParam;
import hack.badgemeal.apis.domain.nft.service.NftService;
import io.swagger.annotations.Api;
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
}
