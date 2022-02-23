package hack.badgemeal.apis.domain.ocr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MetadataBody {
    // 메뉴 이름
    private String name;
    // image Url
    private String image;
    // 인증 날짜
    private String verificationDate;
}
