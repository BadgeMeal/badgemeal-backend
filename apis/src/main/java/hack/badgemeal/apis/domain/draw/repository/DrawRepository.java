package hack.badgemeal.apis.domain.draw.repository;

import hack.badgemeal.apis.common.dto.DrawUserDto;
import hack.badgemeal.apis.domain.draw.model.Draw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {
    Draw findByDrawId(long drawId);

    @Query("SELECT new hack.badgemeal.apis.common.dto.DrawUserDto(u.address, d.drawId, d.count, d.drawInfo.round) " +
            "FROM Draw d INNER JOIN hack.badgemeal.apis.domain.draw.model.User u ON u.address = :address AND d.user.address = :address AND d.round = :round")
    DrawUserDto getDrawByAddressAndRound(String address, long round);
}
