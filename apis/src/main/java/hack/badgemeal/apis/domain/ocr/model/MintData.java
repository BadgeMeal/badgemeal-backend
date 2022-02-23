package hack.badgemeal.apis.domain.ocr.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bm_mint_data")
public class MintData {
    @Id
    private String address;
    // 토큰 ID
    private int tokenId;
    // 메타데이터 URI
    private String metadataUri;
}
