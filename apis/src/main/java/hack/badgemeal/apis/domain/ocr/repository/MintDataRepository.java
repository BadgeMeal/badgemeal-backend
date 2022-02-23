package hack.badgemeal.apis.domain.ocr.repository;

import hack.badgemeal.apis.domain.ocr.model.MintData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MintDataRepository extends JpaRepository<MintData, String> {

}
