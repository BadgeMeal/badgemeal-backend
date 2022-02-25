package hack.badgemeal.apis.domain.menu.service;

import com.klaytn.caver.Caver;
import hack.badgemeal.apis.common.enums.ErrorCode;
import hack.badgemeal.apis.common.exceptions.CustomException;
import hack.badgemeal.apis.common.exceptions.NotFoundException;
import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.model.MenuRequestParam;
import hack.badgemeal.apis.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.web3j.protocol.http.HttpService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    @Value("${badgemeal.caver.access-key-id}")
    private String accessKeyId;
    @Value("${badgemeal.caver.secret-access-key}")
    private String secretAccessKey;
    @Value("${badgemeal.caver.chain-id}")
    private String chainId;

    private final MenuRepository menuRepository;

    public List<Menu> getMenuList() {
        return menuRepository.findAllByMenuNoNotLike(0);
    }

    public List<Menu> postMenuList(List<Menu> menuList) {
        for (int i=0; i<menuList.size(); i++) {
            if (menuRepository.existsByMenuNo(menuList.get(i).getMenuNo())) {
                throw new DataIntegrityViolationException(menuList.get(i).getMenuNo() + "은 이미 등록된 메뉴 넘버(menuNo) 입니다.");
            }
        }

        return menuRepository.saveAll(menuList);
    }

    public Menu postMenu(MenuRequestParam params) {
        try {
            String ipfs_server = "ipfs.infura.io";
            HttpService httpService = new HttpService("https://node-api.klaytnapi.com/v1/klaytn");
            httpService.addHeader("Authorization", Credentials.basic(accessKeyId, secretAccessKey));
            httpService.addHeader("x-chain-id", chainId);
            Caver caver = new Caver(httpService);

            // Set connection with IPFS Node
            caver.ipfs.setIPFSNode(ipfs_server, 5001, true);
            String cid = caver.ipfs.add(params.getImage().getBytes());
            if (cid == null) {
                throw new CustomException(ErrorCode.IPFS_IMAGE_UPLOAD_FAILED);
            }

            String imageUrl = "https://" + ipfs_server + "/ipfs/" + cid;
            Menu menu = new Menu(params.getMenuNo(), params.getType(), imageUrl);

            return menuRepository.save(menu);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new CustomException(ErrorCode.IPFS_IMAGE_UPLOAD_FAILED);
        }
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

