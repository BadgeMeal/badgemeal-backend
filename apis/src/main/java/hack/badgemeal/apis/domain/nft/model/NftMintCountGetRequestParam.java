package hack.badgemeal.apis.domain.nft.model;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NftMintCountGetRequestParam {
    @ApiParam(value = "주소 값", required = true, example = "0x0000000000000000000000000000000000000000", defaultValue = "0x0000000000000000000000000000000000000000")
    private String address;
}
