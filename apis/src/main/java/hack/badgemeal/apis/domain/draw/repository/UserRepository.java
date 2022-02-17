package hack.badgemeal.apis.domain.draw.repository;

import hack.badgemeal.apis.domain.draw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByAddress(String address);
}
