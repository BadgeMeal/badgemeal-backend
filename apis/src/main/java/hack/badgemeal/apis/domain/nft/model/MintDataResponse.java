package hack.badgemeal.apis.domain.nft.model;

import hack.badgemeal.apis.domain.ocr.model.MintData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@Getter
@Setter
@ToString
public class MintDataResponse {
    private MintData mintdata;
    @Id
    @ApiModelProperty(value = "메뉴 이름", example = "김치찌개")
    private String menuType;
}
