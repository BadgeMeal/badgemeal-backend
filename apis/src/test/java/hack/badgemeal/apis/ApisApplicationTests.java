package hack.badgemeal.apis;

import hack.badgemeal.apis.domain.draw.model.Round;
import hack.badgemeal.apis.domain.draw.repository.RoundRepository;
import hack.badgemeal.apis.domain.menu.model.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class ApisApplicationTests {

    @Autowired
    RoundRepository roundRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        Menu menu = new Menu();
        menu.setKeyword("떡볶이");
        menu.setKeyword("떡볶이");
    }

    @Test
    void roundTest() {
        Round nowRound = roundRepository.findByIsNowIsNotNull();
        nowRound.setIsNow(' ');
        roundRepository.save(nowRound);

        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter nowDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Round newRound = new Round();
        newRound.setDate(nowDate.format(nowDateFormat));
        newRound.setIsNow('Y');
        roundRepository.save(newRound);
    }
}
