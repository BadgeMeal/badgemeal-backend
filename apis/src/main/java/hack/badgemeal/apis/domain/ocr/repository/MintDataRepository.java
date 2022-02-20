package hack.badgemeal.apis.domain.ocr.repository;

import hack.badgemeal.apis.domain.ocr.model.MintData;
import hack.badgemeal.apis.domain.ocr.model.MintDataKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MintDataRepository extends JpaRepository<MintData, MintDataKey> {
    boolean existsByAddressAndRound(String address, long round);
}
