package hack.badgemeal.apis.domain.menu.controller;

import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/menus")
    public List<Menu> getMenuList() {
        return menuService.getMenuList();
    }
}
