package hack.badgemeal.apis.domain.draw.model;

import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.ocr.model.MintData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@IdClass(DrawResultKey.class)
@Table(name = "bm_draw_result")
public class DrawResult {
    // 뽑기 결과 매핑 주소
    @Id
    private String address;
    // 뽑기 결과 매핑 회차
    @Id
    private long round;
    // 뽑기 결과 인증 여부
    @ApiModelProperty(value = "뽑기 결과 인증 여부", example = "true")
    private char isVerified;
    // 뽑기 메뉴 NO
    @ManyToOne
    @JoinColumn(name = "menu_menu_no")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "mint_data_mint_data_id")
    private MintData mintData;
}
