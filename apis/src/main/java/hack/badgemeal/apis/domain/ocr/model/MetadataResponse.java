package hack.badgemeal.apis.domain.ocr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataResponse {
    private String contentType;
    private String filename;
    private String uri;
}
