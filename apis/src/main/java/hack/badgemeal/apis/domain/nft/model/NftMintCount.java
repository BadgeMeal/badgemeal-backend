package hack.badgemeal.apis.domain.nft.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@IdClass(NftCountKey.class)
@Table(name = "bm_nft_count")
public class NftMintCount {
    // NFT 발급 주소
    @Id
    @ApiModelProperty(value = "NFT 발급 주소", example = "0x0000000000000000000000000000000000000000")
    private String address;
    // NFT 발급 회차
    @Id
    @ApiModelProperty(value = "NFT 발급 회차", example = "0")
    private long round;
    // NFT 발급 횟수
    @ApiModelProperty(value = "NFT 발급 횟수", example = "0")
    private int count;
}
