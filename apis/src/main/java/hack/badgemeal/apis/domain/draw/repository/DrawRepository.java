package hack.badgemeal.apis.domain.draw.repository;

import hack.badgemeal.apis.domain.draw.model.Draw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {
    Draw findByAddressAndRound(String address, long round);
}
