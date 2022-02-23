package hack.badgemeal.apis.domain.draw.service;

import hack.badgemeal.apis.common.enums.ErrorCode;
import hack.badgemeal.apis.common.enums.ResponseStatus;
import hack.badgemeal.apis.common.enums.VerificationEnum;
import hack.badgemeal.apis.common.exceptions.CustomException;
import hack.badgemeal.apis.common.response.Message;
import hack.badgemeal.apis.domain.draw.model.*;
import hack.badgemeal.apis.domain.draw.repository.DrawRepository;
import hack.badgemeal.apis.domain.draw.repository.DrawResultRepository;
import hack.badgemeal.apis.domain.draw.repository.RoundRepository;
import hack.badgemeal.apis.domain.draw.repository.UserRepository;
import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DrawService {
    private final DrawRepository drawRepository;
    private final RoundRepository roundRepository;
    private final DrawResultRepository drawResultRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public int drawCount(DrawRequestParam params) {
        int count = 0;
        Optional<User> user = userRepository.findByAddress(params.getAddress());
        Round nowRound = roundRepository.findByIsNowIsNotNull();
        if (user.isPresent()) {
            Draw draw = drawRepository.findByAddressAndRound(params.getAddress(), nowRound.getRound());
            if (draw == null) {
                draw = initDraw(params.getAddress(), nowRound.getRound());
            }
            count = draw.getCount();
        } else {
            userRepository.save(new User(params.getAddress()));
            initDraw(params.getAddress(), nowRound.getRound());
        }

        return count;
    }

    public boolean putDrawCount(DrawPutRequestParam params) {
        Optional<User> user = userRepository.findByAddress(params.getAddress());
        Round nowRound = roundRepository.findByIsNowIsNotNull();
        if (user.isPresent()) {
            Draw draw = drawRepository.findByAddressAndRound(params.getAddress(), nowRound.getRound());
            if (draw != null) {
                if (Math.abs(draw.getCount() - params.getCount()) > 1) {
                    throw new CustomException(ErrorCode.DRAW_PUT_COUNT_INVALID);
                }
                draw.setCount(params.getCount());
                drawRepository.save(draw);
            }
        } else {
            userRepository.save(new User(params.getAddress()));
            throw new CustomException(ErrorCode.DATA_NOT_FOUND);
        }

        saveDraw(params.getAddress(), nowRound.getRound(), params.getCount());

        return true;
    }

    public ResponseEntity<?> drawResultIsVerified(DrawRequestParam params) {
        int menuNo = -1;
        Round nowRound = roundRepository.findByIsNowIsNotNull();
        Optional<DrawResult> drawResult = drawResultRepository.findByAddressAndRound(params.getAddress(), nowRound.getRound());

        if (drawResult.isPresent()) {
            Menu menu = drawResult.get().getMenu();
            if (menu != null) {
                menuNo = (int) menu.getMenuNo();
            }
            if (drawResult.get().getIsVerified() == 'Y') {
                return new ResponseEntity<>(
                        Message.builder().status(ResponseStatus.SUCCESS)
                                .data(Map.of("verification", VerificationEnum.TRUE, "menuNo", menuNo)).build(),
                        HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(
                Message.builder().status(ResponseStatus.SUCCESS)
                        .data(Map.of("verification", VerificationEnum.FALSE, "menuNo", menuNo)).build(),
                HttpStatus.OK);
    }

    public DrawResult drawResult(DrawResultRequestParam params) {
        DrawResult drawResult = new DrawResult();
        Optional<Menu> menu = menuRepository.findById(params.getMenuNo());
        Round nowRound = roundRepository.findByIsNowIsNotNull();
        if (!menu.isPresent()) {
            throw new CustomException(ErrorCode.MENU_NOT_FOUND);
        }
        drawResult.setAddress(params.getAddress());
        drawResult.setMenu(menu.get());
        drawResult.setRound(nowRound.getRound());
        drawResult.setIsVerified('N');
        return drawResultRepository.save(drawResult);
    }

    public boolean initDrawResult(String address) {
        Round nowRound = roundRepository.findByIsNowIsNotNull();
        Optional<DrawResult> drawResult = drawResultRepository.findByAddressAndRound(address, nowRound.getRound());
        if (drawResult.isPresent()) {
            drawResult.get().setMenu(null);
            drawResult.get().setIsVerified('N');
            drawResultRepository.save(drawResult.get());
            return true;
        } else {
            throw new CustomException(ErrorCode.DRAW_RESULT_NOT_FOUND);
        }
    }

    private Draw initDraw(String address, long round) {
        Draw draw = new Draw();
        draw.setCount(0);
        draw.setAddress(address);
        draw.setRound(round);

        return drawRepository.save(draw);
    }

    private Draw saveDraw(String address, long round, int count) {
        Draw draw = new Draw();
        draw.setCount(count);
        draw.setAddress(address);
        draw.setRound(round);

        return drawRepository.save(draw);
    }
}
