package hack.badgemeal.apis.domain.ipfs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "bm_master_nft")
@DynamicUpdate
public class MasterNFT implements Serializable{
    // nft ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    // 메뉴 ID
    private long menuNo;
    // 마스터 nft url
    private String imageUrl;
    // 발행여부
    @Column(name="mint_yn", columnDefinition = "TINYINT", length=1)
    private long mintYn;
    // 등록일
    @CreationTimestamp
    private Timestamp regDt;
}
