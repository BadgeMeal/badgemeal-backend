package hack.badgemeal.apis.domain.menu.repository;

import hack.badgemeal.apis.domain.menu.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    boolean existsByMenuNo(long menuNo);
    List<Menu> findAllByMenuNoNotLike(long menuNo);
}
