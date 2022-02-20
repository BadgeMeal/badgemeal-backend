package hack.badgemeal.apis.domain.draw.model;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DrawResultRequestParam {
    @ApiParam(value = "주소 값", required = true, example = "0x0000000000000000000000000000000000000000", defaultValue = "0x0000000000000000000000000000000000000000")
    private String address;
    @ApiParam(value = "메뉴 넘버(ID)", required = true, example = "0", defaultValue = "0")
    private long menuNo;
}
