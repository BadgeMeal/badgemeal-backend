package hack.badgemeal.apis.domain.menu.controller;

import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"메뉴 API"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api")
public class MenuController {
    private final MenuService menuService;

    @ApiOperation(value = "메뉴 리스트를 조회합니다.")
    @GetMapping("/menus")
    public List<Menu> getMenuList() {
        return menuService.getMenuList();
    }
}
