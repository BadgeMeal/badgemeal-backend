package hack.badgemeal.apis.domain.ocr.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class VerifyReceiptRequestParam {
    @ApiModelProperty(value = "영수증 이미지 파일", required = true, example = "receipt.png")
    private MultipartFile image;
    @ApiModelProperty(value = "주소 값", required = true, example = "0x0000000000000000000000000000000000000000")
    private String address;
    @ApiModelProperty(value = "메뉴 넘버(ID)", required = true, example = "0")
    private long menuNo;
}
