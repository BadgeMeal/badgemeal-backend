package hack.badgemeal.apis.domain.draw.service;

import hack.badgemeal.apis.common.dto.DrawUserDto;
import hack.badgemeal.apis.domain.draw.model.User;
import hack.badgemeal.apis.domain.draw.repository.DrawRepository;
import hack.badgemeal.apis.domain.draw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DrawService {
    private final DrawRepository drawRepository;
    private final UserRepository userRepository;

    public int drawCount(String address) {
        int count = 0;
        Optional<User> user = userRepository.findByAddress(address);
        if (user.isPresent()) {
            DrawUserDto drawUserDto = drawRepository.getDrawNowRoundByAddress(address);
            count = (int) drawUserDto.getDrawCount();
        }

        return count;
    }
}
