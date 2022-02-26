package hack.badgemeal.apis.domain.draw.controller;

import hack.badgemeal.apis.domain.draw.model.DrawPutRequestParam;
import hack.badgemeal.apis.domain.draw.model.DrawRequestParam;
import hack.badgemeal.apis.domain.draw.model.DrawResult;
import hack.badgemeal.apis.domain.draw.model.DrawResultRequestParam;
import hack.badgemeal.apis.domain.draw.service.DrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

    @ApiOperation(value = "해당 주소의 랜덤 뽑기 횟수를 수정합니다.")
    @PutMapping("/draw/count")
    public boolean putDrawCount(DrawPutRequestParam drawRequestParam) {
        return drawService.putDrawCount(drawRequestParam);
    }

    @ApiOperation(value = "해당 주소의 랜덤 뽑기 결과 인증 여부를 조회합니다.")
    @GetMapping("/draw/result")
    public ResponseEntity<?> getDrawResultIsVerified(DrawRequestParam drawRequestParam) {
        return drawService.drawResultIsVerified(drawRequestParam);
    }

    @ApiOperation(value = "해당 주소의 랜덤 뽑기 메뉴 번호를 조회합니다.")
    @GetMapping("/draw/menuNo")
    public ResponseEntity<?> getDrawMenuNo(DrawRequestParam drawRequestParam) {
        return drawService.drawMenuNo(drawRequestParam);
    }

    @ApiOperation(value = "해당 주소의 랜덤 뽑기 결과를 저장합니다.")
    @PostMapping("/draw/result")
    public DrawResult postDrawResult(DrawResultRequestParam drawResultRequestParam) {
        return drawService.drawResult(drawResultRequestParam);
    }

    @ApiImplicitParam(name = "address", dataType = "String", defaultValue = "0x0000000000000000000000000000000000000000", example = "0x0000000000000000000000000000000000000000")
    @ApiOperation(value = "해당 주소의 랜덤 뽑기 결과를 초기화합니다.")
    @GetMapping("/draw/result/init")
    public boolean initDrawResult(@RequestParam("address") String address) {
        return drawService.initDrawResult(address);
    }
}
