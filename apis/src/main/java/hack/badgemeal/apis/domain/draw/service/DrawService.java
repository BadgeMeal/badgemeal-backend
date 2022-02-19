package hack.badgemeal.apis.domain.draw.service;

import hack.badgemeal.apis.common.dto.DrawUserDto;
import hack.badgemeal.apis.domain.draw.model.DrawResult;
import hack.badgemeal.apis.domain.draw.model.User;
import hack.badgemeal.apis.domain.draw.repository.DrawInfoRepository;
import hack.badgemeal.apis.domain.draw.repository.DrawRepository;
import hack.badgemeal.apis.domain.draw.repository.DrawResultRepository;
import hack.badgemeal.apis.domain.draw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DrawService {
    private final DrawRepository drawRepository;
    private final DrawInfoRepository drawInfoRepository;
    private final DrawResultRepository drawResultRepository;
    private final UserRepository userRepository;

    public int drawCount(String address) {
        int count = 0;
        Optional<User> user = userRepository.findByAddress(address);
        if (user.isPresent()) {
            int nowRound = (int) drawInfoRepository.findByIsNowIsNotNull();
            DrawUserDto drawUserDto = drawRepository.getDrawByAddressAndRound(address, nowRound);
            count = (int) drawUserDto.getDrawCount();
        } else {
            userRepository.save(new User(address));
        }

        return count;
    }

    public String drawResultIsVerified(String address) {
        int nowRound = (int) drawInfoRepository.findByIsNowIsNotNull();
        Optional<DrawResult> drawResult = drawResultRepository.findByAddressAndRound(address, nowRound);
        if (drawResult.isPresent()) {
            if (drawResult.get().getIsVerified() == 'Y') {
                return "true";
            } else {
                return "false";
            }
        }

        return "false";
    }
}
