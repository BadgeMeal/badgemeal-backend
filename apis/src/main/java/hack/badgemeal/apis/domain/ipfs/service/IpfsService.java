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
        HttpService httpService = new HttpService("https://node-api.klaytnapi.com/v1/klaytn");
        httpService.addHeader("Authorization", Credentials.basic(accessKeyId, secretAccessKey));
        httpService.addHeader("x-chain-id", chainId);
        Caver caver = new Caver(httpService);

        // Set connection with IPFS Node
        caver.ipfs.setIPFSNode("ipfs.infura.io", 5001, true);
        String cid = caver.ipfs.add(image.getBytes());
        String multihash = caver.ipfs.toHex(cid);
        return multihash;
    }

    public String downloadFromIPFS(String multihash) throws IOException {
        HttpService httpService = new HttpService("https://node-api.klaytnapi.com/v1/klaytn");
        httpService.addHeader("Authorization", Credentials.basic(accessKeyId, secretAccessKey));
        httpService.addHeader("x-chain-id", chainId);
        Caver caver = new Caver(httpService);

        String cid = caver.ipfs.fromHex(multihash);
        byte[] contents = caver.ipfs.get(cid);
        System.out.println("Contents downloaded from IPFS: " + new String(contents, StandardCharsets.UTF_8));

        return contents.toString();
    }
    public void run() throws Exception {
        HttpService httpService = new HttpService("https://node-api.klaytnapi.com/v1/klaytn");
        httpService.addHeader("Authorization", Credentials.basic(accessKeyId, secretAccessKey));
        httpService.addHeader("x-chain-id", chainId);
        Caver caver = new Caver(httpService);

        // Set connection with IPFS Node
        caver.ipfs.setIPFSNode("ipfs.infura.io", 5001, true);
        // `ipfs.txt` is located at `caver-java-examples/ipfs/using_ipfs_with_caver/resources`.
        File testFile = new File("resources/ipfs.txt");
        if (testFile.exists() == false) {
            // Handles when you run this CaverExample as sub-module using IDE.
            testFile = new File("ipfs/using_ipfs_with_caver/resources/ipfs.txt");
            if (testFile.exists() == false) {
                throw new FileNotFoundException("Cannot find ipfs.txt testFile.");
            }
        }

        // Add a file to IPFS with file path
        // String cid = caver.ipfs.add(testFile.getAbsolutePath());


        // // Add a testFile to IPFS with testFile contents
        String text = "IPFS test";
        byte[] data = text.getBytes();
        String cid = caver.ipfs.add(data);
        System.out.println("cid: " + cid);

        // Download a testFile from IPFS
        byte[] contents = caver.ipfs.get(cid);
        System.out.println("Contents downloaded from IPFS: " + new String(contents, StandardCharsets.UTF_8));

        // Convert from CID to multihash(hex formatted)
        String multihash = caver.ipfs.toHex(cid);
        System.out.println("multihash: " + multihash);
    }
}
