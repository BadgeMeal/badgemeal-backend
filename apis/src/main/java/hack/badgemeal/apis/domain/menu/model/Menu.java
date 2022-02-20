package hack.badgemeal.apis.domain.menu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "bm_menu")
public class Menu {
    // 메뉴 ID
    @Id
    @ApiModelProperty(value = "메뉴 넘버(ID)", example = "0", required = true)
    private long menuNo;
    // 메뉴 타입
    @ApiModelProperty(value = "메뉴 종류(타입)", example = "치킨")
    private String type;
    // 메뉴 키워드
    @ApiModelProperty(value = "키워드(OCR 인증시 검색할 단어)", example = "치킨")
    private String keyword;
}
