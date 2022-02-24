package hack.badgemeal.apis.domain.nft.service;

import hack.badgemeal.apis.common.enums.ErrorCode;
import hack.badgemeal.apis.common.exceptions.CustomException;
import hack.badgemeal.apis.domain.draw.model.DrawResult;
import hack.badgemeal.apis.domain.draw.model.Round;
import hack.badgemeal.apis.domain.draw.model.User;
import hack.badgemeal.apis.domain.draw.repository.DrawResultRepository;
import hack.badgemeal.apis.domain.draw.repository.RoundRepository;
import hack.badgemeal.apis.domain.draw.repository.UserRepository;
import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.repository.MenuRepository;
import hack.badgemeal.apis.domain.nft.model.*;
import hack.badgemeal.apis.domain.nft.repository.NftCountRepository;
import hack.badgemeal.apis.domain.ocr.model.MintData;
import hack.badgemeal.apis.domain.ocr.repository.MintDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NftService {
    private final RoundRepository roundRepository;
    private final UserRepository userRepository;
    private final NftCountRepository nftCountRepository;
    private final MintDataRepository mintDataRepository;
    private final DrawResultRepository drawResultRepository;
    private final MenuRepository menuRepository;

    public int nftCount(NftMintCountGetRequestParam params) {
        int count = 0;
        Optional<User> user = userRepository.findByAddress(params.getAddress());
        Round nowRound = roundRepository.findByIsNowIsNotNull();
        if (user.isPresent()) {
            NftMintCount nftMintCount = nftCountRepository.findByAddressAndRound(params.getAddress(), nowRound.getRound());
            if (nftMintCount == null) {
                nftMintCount = saveNftMintCount(params.getAddress(), nowRound.getRound());
            }
            count = nftMintCount.getCount();
        } else {
            userRepository.save(new User(params.getAddress()));
            saveNftMintCount(params.getAddress(), nowRound.getRound());
        }

        return count;
    }

    public NftMintCount nftCount(NftMintCountPutRequestParam params) {
        Optional<User> user = userRepository.findByAddress(params.getAddress());
        Round nowRound = roundRepository.findByIsNowIsNotNull();
        if (user.isPresent()) {
            NftMintCount nftMintCount = nftCountRepository.findByAddressAndRound(params.getAddress(), nowRound.getRound());
            nftMintCount.setCount(params.getCount());
            return nftCountRepository.save(nftMintCount);
        } else {
            throw new CustomException(ErrorCode.DATA_NOT_FOUND);
        }
    }

    public MintDataResponse mintData(String address) {
        MintDataResponse mintDataResponse = new MintDataResponse();
        Round nowRound = roundRepository.findByIsNowIsNotNull();
        Optional<MintData> mintData = mintDataRepository.findById(address);
        Optional<DrawResult> drawResult = drawResultRepository.findByAddressAndRound(address, nowRound.getRound());

        if (drawResult.isPresent()) {
            if (drawResult.get().getMenu() != null) {
                Optional<Menu> menu = menuRepository.findById(drawResult.get().getMenu().getMenuNo());
                mintDataResponse.setMenuType(menu.get().getType());
            } else {
                log.error("해당 주소의 뽑기 결과에 매핑된 메뉴가 존재하지 않습니다.");
                throw new CustomException(ErrorCode.DATA_NOT_FOUND);
            }
        } else {
            log.error("해당 주소의 뽑기 결과가 존재하지 않습니다.");
            throw new CustomException(ErrorCode.DATA_NOT_FOUND);
        }

        if (mintData.isPresent()) {
            mintDataResponse.setMintdata(mintData.get());
        } else {
            log.error("해당 주소로 매핑된 MintData가 존재하지 않습니다.");
            throw new CustomException(ErrorCode.DATA_NOT_FOUND);
        }

        return mintDataResponse;
    }

    public MintData postMintData(MintDataPostRequestParam param) {
        return mintDataRepository.save(new MintData(param.getAddress(), param.getTokenId(), param.getImageUrl()));
    }

    public boolean initMintData(String address) {
        Optional<MintData> mintData = mintDataRepository.findById(address);
        if (mintData.isPresent()) {
            mintDataRepository.deleteById(address);
        }

        return true;
    }

    private NftMintCount saveNftMintCount(String address, long round) {
        NftMintCount newNftMintCount = new NftMintCount();
        newNftMintCount.setCount(0);
        newNftMintCount.setAddress(address);
        newNftMintCount.setRound(round);

        return nftCountRepository.save(newNftMintCount);
    }


}
