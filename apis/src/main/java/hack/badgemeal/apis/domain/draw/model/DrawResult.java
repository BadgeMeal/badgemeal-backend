package hack.badgemeal.apis.domain.draw.model;

import hack.badgemeal.apis.domain.menu.model.Menu;
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
@Table(name = "bm_draw_result")
public class DrawResult {
    // 뽑기 결과 매핑 주소
    @Id
    private String address;
    // 뽑기 결과 매핑 회차
    @Id
    private String round;
    // 뽑기 결과 인증 여부
    private char isVerified;
    // 뽑기 메뉴 NO
    @ManyToOne
    @JoinColumn(name = "menu_no_menu_no")
    private Menu menuNo;
}
