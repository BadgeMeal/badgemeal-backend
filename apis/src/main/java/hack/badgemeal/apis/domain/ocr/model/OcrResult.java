package hack.badgemeal.apis.domain.ocr.model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OcrResult {
    private ArrayList<Object> boxes;
    private ArrayList<String> recognition_words;
}
