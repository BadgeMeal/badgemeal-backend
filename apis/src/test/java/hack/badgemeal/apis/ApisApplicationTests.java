package hack.badgemeal.apis;

import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.repository.MenuRepository;
import hack.badgemeal.apis.domain.menu.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApisApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        Menu menu = new Menu();
        menu.setKeyword("떡볶이");
        menu.setKeyword("떡볶이");
    }
}
