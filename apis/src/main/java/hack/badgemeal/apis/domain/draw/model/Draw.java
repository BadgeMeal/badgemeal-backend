package hack.badgemeal.apis.domain.draw.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@IdClass(DrawResultKey.class)
@Table(name = "bm_draw")
public class Draw {
    // 랜덤 뽑기 주소
    @Id
    @ApiModelProperty(value = "랜덤 뽑기 주소", example = "0x0000000000000000000000000000000000000000")
    private String address;
    // 랜덤 뽑기 회차
    @Id
    @ApiModelProperty(value = "랜덤 뽑기 회차", example = "0")
    private long round;
    // 랜덤 뽑기 횟수
    @ApiModelProperty(value = "뽑기 횟수", example = "0")
    private int count;
}
