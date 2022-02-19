package hack.badgemeal.apis.domain.draw.model;

import hack.badgemeal.apis.domain.menu.model.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@IdClass(DrawResultKey.class)
@Table(name = "bm_draw_result")
public class DrawResult implements Serializable {
    private static final long serialVersionUID = 1L;

    // 뽑기 결과 매핑 주소
    @Id
    private String address;
    // 뽑기 결과 매핑 회차
    @Id
    private long round;
    // 뽑기 결과 인증 여부
    private char isVerified;
    // 뽑기 메뉴 NO
    @ManyToOne
    @JoinColumn(name = "menu_no_menu_no")
    private Menu menuNo;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof DrawResult) && address == ((DrawResult)o).getAddress() && round == ((DrawResult) o).getRound());
    }

    @Override
    public int hashCode() {
        return (int)(Integer.valueOf(address) ^ round);
    }
}
