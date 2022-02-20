package hack.badgemeal.apis.domain.ocr.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(MintDataKey.class)
@Table(name = "bm_mint_data")
public class MintData implements Serializable {
    private static final long serialVersionUID = 1L;

    // 발행데이터 주소
    @Id
    private String address;
    // 발행데이터 회차
    @Id
    private long round;
    // 토큰 ID
    private int tokenId;
    // 메타데이터 URI
    private String metadataUri;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof MintData) && address == ((MintData)o).getAddress() && round == ((MintData) o).getRound());
    }

    @Override
    public int hashCode() {
        return (int)(Integer.valueOf(address) ^ round);
    }
}
