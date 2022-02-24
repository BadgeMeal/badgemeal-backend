package hack.badgemeal.apis.domain.nft.repository;

import hack.badgemeal.apis.domain.nft.model.NftMintCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftCountRepository extends JpaRepository<NftMintCount, Long> {
    NftMintCount findByAddressAndRound(String address, long round);
}
