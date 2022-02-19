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

@Service
@Slf4j
@RequiredArgsConstructor
public class IpfsService {
    @Value("${caver:access-key-id}")
    private String accessKeyId;
    @Value("${caver:secret-access-key}")
    private String secretAccessKey;
    @Value("${caver:chain-id}")
    private String chainId;

    public String uploadToIPFS(MultipartFile image) throws IOException {
        String ipfs_server = "ipfs.infura.io";
        HttpService httpService = new HttpService("https://node-api.klaytnapi.com/v1/klaytn");
        httpService.addHeader("Authorization", Credentials.basic(accessKeyId, secretAccessKey));
        httpService.addHeader("x-chain-id", chainId);
        Caver caver = new Caver(httpService);

        // Set connection with IPFS Node
        caver.ipfs.setIPFSNode(ipfs_server, 5001, true);
        String cid = caver.ipfs.add(image.getBytes());
        return "https://" +ipfs_server + "/ipfs/" + cid;
    }
}
