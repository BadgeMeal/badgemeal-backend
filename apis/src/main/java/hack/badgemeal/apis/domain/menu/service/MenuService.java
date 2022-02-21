package hack.badgemeal.apis.domain.menu.service;

import hack.badgemeal.apis.common.exceptions.NotFoundException;
import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public List<Menu> getMenuList() {
        return menuRepository.findAll();
    }

    public List<Menu> postMenuList(List<Menu> menuList) {
        for (int i=0; i<menuList.size(); i++) {
            if (menuRepository.existsByMenuNo(menuList.get(i).getMenuNo())) {
                throw new DataIntegrityViolationException(menuList.get(i).getMenuNo() + "은 이미 등록된 메뉴 넘버(menuNo) 입니다.");
            }
        }

        return menuRepository.saveAll(menuList);
    }

    public Menu putMenu(Menu menu) {
        if (!menuRepository.existsByMenuNo(menu.getMenuNo())) {
            throw new NotFoundException(menu.getMenuNo() + "은 등록되지 않은 메뉴 넘버(menuNo) 입니다.");
        }
        return menuRepository.save(menu);
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

}

