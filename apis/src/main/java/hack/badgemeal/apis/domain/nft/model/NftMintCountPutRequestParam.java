package hack.badgemeal.apis.domain.nft.model;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NftMintCountPutRequestParam {
    @ApiParam(value = "주소 값", required = true, example = "0x0000000000000000000000000000000000000000", defaultValue = "0x0000000000000000000000000000000000000000")
    private String address;
    @ApiParam(value = "수정할 NFT 발급 횟수", required = true, example = "1", defaultValue = "1")
    private int count;
}
