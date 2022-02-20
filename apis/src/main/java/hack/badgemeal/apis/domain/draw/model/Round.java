package hack.badgemeal.apis.domain.draw.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "bm_round")
public class Round {
    // 뽑기 회차
    @Id
    private long round;
    // 뽑기 날짜
    private String date;
    // 현재 뽑기 회차 여부
    @Column(unique = true)
    private char isNow;
}
