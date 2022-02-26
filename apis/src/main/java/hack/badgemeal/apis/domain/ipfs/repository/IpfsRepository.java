package hack.badgemeal.apis.domain.ipfs.repository;

import hack.badgemeal.apis.domain.ipfs.model.MasterNFT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IpfsRepository extends JpaRepository<MasterNFT, Long> {
    List<MasterNFT> findByMenuNoAndMintYn(Long menu_no, Long mint_yn);
    MasterNFT findByCidAndMintYn(String cid, Long mint_yn);
    Optional<MasterNFT> findByImageUrl(String image_url);
    Optional<MasterNFT> findByCid(String cid);
}

