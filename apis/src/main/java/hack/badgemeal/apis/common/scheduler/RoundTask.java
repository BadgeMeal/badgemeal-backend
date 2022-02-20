package hack.badgemeal.apis.common.scheduler;

import hack.badgemeal.apis.domain.draw.model.Round;
import hack.badgemeal.apis.domain.draw.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class RoundTask {
    private final RoundRepository roundRepository;

    /*매일 자정 실행*/
    @Scheduled(cron = "0 0 0 * * *")
    public void setRound() {
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
