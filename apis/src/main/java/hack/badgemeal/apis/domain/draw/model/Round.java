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
@Table(name = "bm_round")
public class Round {
    // 뽑기 회차
    @Id
    @GeneratedValue
    private long round;
    // 뽑기 날짜
    private String date;
    // 현재 뽑기 회차 여부
    @Convert(converter = EmptyStringToNullConverter.class)
    @Column(unique = true)
    private char isNow;
}
