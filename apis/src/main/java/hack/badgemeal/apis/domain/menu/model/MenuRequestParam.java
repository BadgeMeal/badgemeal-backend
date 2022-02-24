package hack.badgemeal.apis.domain.menu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class MenuRequestParam {
    // 메뉴 ID
    @ApiModelProperty(value = "메뉴 넘버(ID)", example = "0", required = true)
    private long menuNo;
    // 메뉴 타입
    @ApiModelProperty(value = "메뉴 종류(타입)", example = "치킨", required = true)
    private String type;
    // 메뉴 이미지
    @ApiModelProperty(value = "이미지", required = true)
    private MultipartFile image;
}
