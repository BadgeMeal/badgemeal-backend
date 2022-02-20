package hack.badgemeal.apis.domain.ocr.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    // 발행데이터 ID
    @Id
    @GeneratedValue
    private long mintDataId;
    // 토큰 ID
    private int tokenId;
    // 메타데이터 URI
    private String metadataUri;
    // 소유자 address
    private String address;
}
