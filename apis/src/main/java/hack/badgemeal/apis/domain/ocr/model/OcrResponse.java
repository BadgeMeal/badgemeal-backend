package hack.badgemeal.apis.domain.ocr.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OcrResponse {
    private List<OcrResult> result;
}
