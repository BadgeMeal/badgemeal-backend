package hack.badgemeal.apis.domain.ipfs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.klaytn.caver.Caver;
import com.klaytn.caver.methods.response.TransactionReceipt;
import com.klaytn.caver.transaction.TxPropertyBuilder;
import com.klaytn.caver.transaction.response.PollingTransactionReceiptProcessor;
import com.klaytn.caver.transaction.response.TransactionReceiptProcessor;
import com.klaytn.caver.transaction.type.ValueTransferMemo;
import com.klaytn.caver.wallet.keyring.SingleKeyring;
import hack.badgemeal.apis.common.dto.ResultDto;
import hack.badgemeal.apis.domain.ipfs.model.MasterNFT;
import hack.badgemeal.apis.domain.ipfs.repository.IpfsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class IpfsService {
    private final IpfsRepository ipfsRepository;

    @Value("${caver.access-key-id}")
    private String accessKeyId;
    @Value("${caver.secret-access-key}")
    private String secretAccessKey;
    @Value("${caver.chain-id}")
    private String chainId;

    public ResultDto uploadToIPFS(Long menu_no, MultipartFile image) {
        ResultDto resultDto = new ResultDto();
        String status = "fail";

        try{
            String ipfs_server = "ipfs.infura.io";
            HttpService httpService = new HttpService("https://node-api.klaytnapi.com/v1/klaytn");
            httpService.addHeader("Authorization", Credentials.basic(accessKeyId, secretAccessKey));
            httpService.addHeader("x-chain-id", chainId);
            Caver caver = new Caver(httpService);

            // Set connection with IPFS Node
            caver.ipfs.setIPFSNode(ipfs_server, 5001, true);
            String cid = caver.ipfs.add(image.getBytes());
            String img_url = "https://" +ipfs_server + "/ipfs/" + cid;

            MasterNFT masterNFT = new MasterNFT();
            masterNFT.setMenuNo(menu_no);
            masterNFT.setImageUrl(img_url);

            Optional<MasterNFT> checkMasterNFT = ipfsRepository.findByImageUrl(img_url);
            if(!checkMasterNFT.isPresent()){
                ipfsRepository.save(masterNFT);
                status = "success";
            }else{
                status = "fail";
                resultDto.setMsg("already registered");
            }

        }catch (Exception e){
            e.printStackTrace();
            status = "fail";
            resultDto.setMsg(e.getMessage());
        }

        resultDto.setStatus(status);

        return resultDto;
    }

    public ResultDto getIpfsUrlByMenuNo (Long menu_no){
        ResultDto resultDto = new ResultDto();

        String result = null;
        List<MasterNFT> masterNFT = ipfsRepository.findByMenuNoAndMintYn(menu_no, 0L);
        if(masterNFT.size() > 0){
            result = masterNFT.get(0).getImageUrl();
            masterNFT.get(0).setMintYn(1);
            ipfsRepository.save(masterNFT.get(0));
            resultDto.setStatus("success");
            resultDto.setResult(result);
        }else{
            resultDto.setStatus("fail");
            resultDto.setMsg("There's no NFT Image.");
        }

        return resultDto;
    }



}
