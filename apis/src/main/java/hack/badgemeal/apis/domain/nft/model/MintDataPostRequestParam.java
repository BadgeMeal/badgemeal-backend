package hack.badgemeal.apis.domain.nft.model;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MintDataPostRequestParam {
    @ApiParam(value = "주소 값", required = true, example = "0x0000000000000000000000000000000000000000", defaultValue = "0x0000000000000000000000000000000000000000")
    private String address;
    @ApiParam(value = "토큰 ID", required = true, example = "224241451", defaultValue = "0")
    private int tokenId;
    @ApiParam(value = "이미지 URL", required = true, example = "https://image-url")
    private String imageUrl;
}
