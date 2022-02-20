package hack.badgemeal.apis.domain.draw.repository;

import hack.badgemeal.apis.domain.draw.model.DrawResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrawResultRepository extends JpaRepository<DrawResult, Long> {
    Optional<DrawResult> findByAddressAndRound(String address, long round);
}
