package hack.badgemeal.apis.domain.menu.controller;

import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.model.MenuRequestParam;
import hack.badgemeal.apis.domain.menu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "메뉴 리스트를 저장합니다.")
    @PostMapping("/menus")
    public List<Menu> postMenuList(@RequestBody List<Menu> menuList) {
        return menuService.postMenuList(menuList);
    }

    @ApiOperation(value = "메뉴를 저장합니다. (with IPFS 업로드)")
    @PostMapping("/menu")
    public Menu postMenu(MenuRequestParam menu) {
        return menuService.postMenu(menu);
    }

    @ApiOperation(value = "메뉴를 수정합니다.")
    @PutMapping("/menu")
    public Menu putMenuList(Menu menu) {
        return menuService.putMenu(menu);
    }
}
