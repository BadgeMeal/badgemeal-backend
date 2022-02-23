package hack.badgemeal.apis.domain.nft.service;

import hack.badgemeal.apis.common.enums.ErrorCode;
import hack.badgemeal.apis.common.exceptions.CustomException;
import hack.badgemeal.apis.domain.draw.model.Round;
import hack.badgemeal.apis.domain.draw.model.User;
import hack.badgemeal.apis.domain.draw.repository.RoundRepository;
import hack.badgemeal.apis.domain.draw.repository.UserRepository;
import hack.badgemeal.apis.domain.nft.model.MintDataPostRequestParam;
import hack.badgemeal.apis.domain.nft.model.NftMintCount;
import hack.badgemeal.apis.domain.nft.model.NftMintCountGetRequestParam;
import hack.badgemeal.apis.domain.nft.model.NftMintCountPutRequestParam;
import hack.badgemeal.apis.domain.nft.repository.NftCountRepository;
import hack.badgemeal.apis.domain.ocr.model.MintData;
import hack.badgemeal.apis.domain.ocr.repository.MintDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NftService {
    private final RoundRepository roundRepository;
    private final UserRepository userRepository;
    private final NftCountRepository nftCountRepository;
    private final MintDataRepository mintDataRepository;

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

    public MintData mintData(String address) {
        Optional<MintData> mintData = mintDataRepository.findById(address);
        if (mintData.isPresent()) {
            return mintData.get();
        } else {
            throw new CustomException(ErrorCode.DATA_NOT_FOUND);
        }
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
