package hack.badgemeal.apis.domain.menu.model;

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
    private long menuNo;
    // 메뉴 타입
    private String type;
    // 메뉴 키워드
    private String keyword;
}
