package hack.badgemeal.apis.domain.draw.controller;

import hack.badgemeal.apis.domain.draw.model.DrawRequestParam;
import hack.badgemeal.apis.domain.draw.model.DrawResult;
import hack.badgemeal.apis.domain.draw.model.DrawResultRequestParam;
import hack.badgemeal.apis.domain.draw.service.DrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"랜덤 뽑기 API"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api")
public class DrawController {
    private final DrawService drawService;

    @ApiOperation(value = "해당 주소의 랜덤 뽑기 횟수를 조회합니다.")
    @GetMapping("/draw/count")
    public int getDrawCount(DrawRequestParam drawRequestParam) {
        return drawService.drawCount(drawRequestParam);
    }

    @ApiOperation(value = "해당 주소의 랜덤 뽑기 결과 인증 여부를 조회합니다.")
    @GetMapping("/draw/result")
    public ResponseEntity<?> getDrawResultIsVerified(DrawRequestParam drawRequestParam) {
        return drawService.drawResultIsVerified(drawRequestParam);
    }

    @ApiOperation(value = "해당 주소의 랜덤 뽑기 결과를 저장합니다.")
    @PostMapping("/draw/result")
    public DrawResult postDrawResult(DrawResultRequestParam drawResultRequestParam) {
        return drawService.drawResult(drawResultRequestParam);
    }
}
