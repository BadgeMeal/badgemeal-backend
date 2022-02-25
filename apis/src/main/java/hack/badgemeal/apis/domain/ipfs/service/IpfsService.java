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
import hack.badgemeal.apis.common.enums.ErrorCode;
import hack.badgemeal.apis.common.enums.ResponseStatus;
import hack.badgemeal.apis.common.enums.VerificationEnum;
import hack.badgemeal.apis.common.exceptions.CustomException;
import hack.badgemeal.apis.common.response.Message;
import hack.badgemeal.apis.domain.ipfs.model.MasterNFT;
import hack.badgemeal.apis.domain.ipfs.repository.IpfsRepository;
import hack.badgemeal.apis.domain.ocr.model.MetadataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import okhttp3.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.web3j.protocol.http.HttpService;
import reactor.util.LinkedMultiValueMap;
import reactor.util.MultiValueMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
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
    @Value("${badgemeal.caver.chain-id}")
    private String chainId;

    public ResponseEntity<Message> uploadToIPFS(Long menu_no, String title, String description, MultipartFile image) {
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


            // metaData 생성
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", title);
            jsonObject.put("description", description);
            jsonObject.put("image", img_url);

            JSONObject parmJson = new JSONObject();
            parmJson.put("metadata", jsonObject);

            WebClient webClient = WebClient.builder()
                    .baseUrl("https://metadata-api.klaytnapi.com/v1")
                    .build();

            MetadataResponse metadataResponse = webClient.post()
                    .uri("/metadata")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("x-chain-id", chainId)
                    .header("authorization", Credentials.basic(accessKeyId, secretAccessKey))
                    .bodyValue(parmJson)
                    .retrieve()
                    .bodyToMono(MetadataResponse.class)
                    .block();

            String meta_data = metadataResponse.getUri();

            MasterNFT masterNFT = new MasterNFT();
            masterNFT.setMenuNo(menu_no);
            masterNFT.setImageUrl(meta_data);

            if (metadataResponse == null) {
                throw new CustomException(ErrorCode.KAS_METADATA_API);
            }

            Optional<MasterNFT> checkMasterNFT = ipfsRepository.findByImageUrl(meta_data);
            if(!checkMasterNFT.isPresent()){
                ipfsRepository.save(masterNFT);
                return new ResponseEntity<>(
                        Message.builder().status(ResponseStatus.SUCCESS)
                                .data(Map.of("msg", "saved")).build(),
                        HttpStatus.OK);
            }else{
                throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(ErrorCode.KAS_API);
        }
    }

    public ResponseEntity<Message> getIpfsUrlByMenuNo (Long menu_no){

        String result = null;
        List<MasterNFT> masterNFT = ipfsRepository.findByMenuNoAndMintYn(menu_no, 0L);
        if(masterNFT.size() > 0){
            result = masterNFT.get(0).getImageUrl();
            masterNFT.get(0).setMintYn(1);
            ipfsRepository.save(masterNFT.get(0));
            return new ResponseEntity<>(
                    Message.builder().status(ResponseStatus.SUCCESS)
                            .data(Map.of("result", result)).build(),
                    HttpStatus.OK);
        }else{
            throw new CustomException(ErrorCode.MASTER_NFT_IMG_NOT_FOUND);
        }
    }



}
