package hack.badgemeal.apis.domain.draw.model;

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
@Table(name = "bm_draw")
public class Draw {
    // 뽑기 ID
    @Id
    @GeneratedValue
    private long drawId;
    // 뽑기 횟수
    private long count;
    // 뽑기 회차
    @ManyToOne
    @JoinColumn(name = "round_round")
    private Round round;
    // 유저 ID
    @ManyToOne
    @JoinColumn(name = "user_address")
    private User user;
}
