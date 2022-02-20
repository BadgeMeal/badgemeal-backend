package hack.badgemeal.apis.common.dto;

import lombok.*;

@Getter
@Setter
public class ResultDto {
    // 상태
    public String status;
    // msg
    public String msg;
    // 결과
    public String result;
}
