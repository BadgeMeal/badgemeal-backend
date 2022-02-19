package hack.badgemeal.apis.domain.draw.repository;

import hack.badgemeal.apis.domain.draw.model.DrawInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawInfoRepository extends JpaRepository<DrawInfo, Long> {
    long findByIsNowIsNotNull();
}
