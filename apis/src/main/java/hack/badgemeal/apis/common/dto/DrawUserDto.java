package hack.badgemeal.apis.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DrawUserDto {
    private String address;
    private long drawId;
    private long drawCount;
    private long drawRound;
}
