package hack.badgemeal.apis.domain.draw.model;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DrawPutRequestParam {
    @ApiParam(value = "주소 값", required = true, example = "0x0000000000000000000000000000000000000000", defaultValue = "0x0000000000000000000000000000000000000000")
    private String address;
    @ApiParam(value = "카운트 값", required = true, example = "1", defaultValue = "1")
    private int count;
}
