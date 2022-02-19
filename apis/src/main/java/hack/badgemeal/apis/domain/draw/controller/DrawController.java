package hack.badgemeal.apis.domain.draw.controller;

import hack.badgemeal.apis.domain.draw.service.DrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api")
public class DrawController {
    private final DrawService drawService;

    @GetMapping("/draw/count")
    public int getDrawCount(@RequestParam("address") String address) {
        return drawService.drawCount(address);
    }

    @GetMapping("/draw/result")
    public String getDrawResultIsVerified(@RequestParam("address") String address) {
        return drawService.drawResultIsVerified(address);
    }
}
