package hack.badgemeal.apis.domain.draw.repository;

import hack.badgemeal.apis.domain.draw.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    Round findByIsNowIsNotNull();
}
