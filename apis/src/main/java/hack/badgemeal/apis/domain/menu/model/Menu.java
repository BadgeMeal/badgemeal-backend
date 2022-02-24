package hack.badgemeal.apis.domain.menu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
    // 메뉴 이미지 URL
    @ApiModelProperty(value = "이미지 URL", example = "https://metadata-store.klaytnapi.com/7f4f7e2f-fc73-884b-b43f-b26cf625f31f/26210a29-d469-5aeb-c2c1-09d0a763f206.jpg")
    private String imageUrl;
}
