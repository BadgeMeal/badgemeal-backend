package hack.badgemeal.apis.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.klaytn.caver.Caver;
import com.klaytn.caver.abi.datatypes.Utf8String;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.contract.SendOptions;
import com.klaytn.caver.methods.response.TransactionReceipt;
import com.klaytn.caver.wallet.keyring.SingleKeyring;
import okhttp3.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;

@Service
public class CaverJava {
    private static String accessKeyId;
    @Value("${caver.access-key-id}")
    public void setaccessKeyId(String key) {
        accessKeyId = key;
    }

    private static String secretAccessKey;
    @Value("${caver.secret-access-key}")
    public void setsecretAccessKey(String key) {
        secretAccessKey = key;
    }

    private static String chainId;
    @Value("${caver.chain-id}")
    public void setchainId(String id) {
        chainId = id;
    }

    private static String walletAddress;
    @Value("${badgemeal.wallet-address}")
    public void setwalletAddress(String address) {
        walletAddress = address;
    }

    private static String walletPrivateKey;
    @Value("${badgemeal.wallet-private-key}")
    public void setwalletPrivateKey(String privateKey) {
        walletPrivateKey = privateKey;
    }


    private Caver connectCaver(){
        HttpService httpService = new HttpService("https://node-api.klaytnapi.com/v1/klaytn");
        httpService.addHeader("Authorization", Credentials.basic(accessKeyId, secretAccessKey));
        httpService.addHeader("x-chain-id", chainId);
        Caver caver = new Caver(httpService);

        return caver;
    }
    private static Contract connectContract(Caver caver, String abi) throws IOException {
        Contract contract = caver.contract.create(abi);
        return contract;
    }


    private static Contract connectContract(Caver caver, String abi, String contractAddress) throws IOException {
        System.out.println("abi : " + abi);
        Contract contract = caver.contract.create(abi, contractAddress);
        return contract;
    }

    private SingleKeyring getKeyring(Caver caver){
        SingleKeyring keyring = caver.wallet.keyring.create(walletAddress, walletPrivateKey);
        return keyring;
    }

    private static String objectToString(Object value) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(value);
    }

    public String callContractFunc(String contractAddress, String abi, String functionName) throws IOException, TransactionException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Caver caver = this.connectCaver();
        SingleKeyring keyring = this.getKeyring(caver);
        caver.wallet.add(keyring);
        Contract contract = connectContract(caver, abi, contractAddress);

        SendOptions sendOptionsForExecution = new SendOptions();
        sendOptionsForExecution.setFrom(keyring.getAddress());
        sendOptionsForExecution.setGas(BigInteger.valueOf(1000000));

        TransactionReceipt.TransactionReceiptData receiptData = contract.send(sendOptionsForExecution, functionName);
        System.out.println(objectToString(receiptData));
        return objectToString(receiptData);
    }
    public String callContractFunc(String contractAddress, String abi, String functionName, String parm) throws IOException, TransactionException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Caver caver = this.connectCaver();
        SingleKeyring keyring = this.getKeyring(caver);
        caver.wallet.add(keyring);
        Contract contract = connectContract(caver, abi, contractAddress);

        SendOptions sendOptionsForExecution = new SendOptions();
        sendOptionsForExecution.setFrom(keyring.getAddress());
        sendOptionsForExecution.setGas(BigInteger.valueOf(1000000));

        TransactionReceipt.TransactionReceiptData receiptData = contract.send(sendOptionsForExecution, functionName, parm);
        System.out.println(objectToString(receiptData));
        return objectToString(receiptData);
    }

    public String callContractFuncView(String contractAddress, String abi, String functionName) throws IOException, TransactionException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Caver caver = this.connectCaver();
        SingleKeyring keyring = this.getKeyring(caver);
        caver.wallet.add(keyring);
        Contract contract = connectContract(caver, abi, contractAddress);

        Utf8String result = (Utf8String) contract.call(functionName).get(0);
        return result.toString();
    }

    /*
    예시
    public void callContractFunc(String contractAddress, String abi) throws TransactionException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // Add keyrings to in-memory wallet
        Caver caver = connectCaver();
        SingleKeyring keyring =getKeyring(caver, walletAddress, walletPrivateKey);
        caver.wallet.add(keyring);
        Contract contract = connectContract(caver, abi);


        // Send a FeeDelegatedSmartContractDeploy transaction to Klaytn directly.
        SendOptions sendOptionsForDeployment = new SendOptions();
        sendOptionsForDeployment.setFrom(keyring.getAddress());
        sendOptionsForDeployment.setGas(BigInteger.valueOf(1000000));
        sendOptionsForDeployment.setFeeDelegation(true);
        sendOptionsForDeployment.setFeePayer(keyring.getAddress());


        contract.deploy(sendOptionsForDeployment, contractAddress);
        System.out.println("The address of deployed smart contract: " + contract.getContractAddress());

        //function view
        // Read value string from the smart contract.
        Utf8String valueString = (Utf8String) contract.call("get", "keyString").get(0);

        //function
        // Send a FeeDelegatedSmartContractExecutionWithRatio transaction to the Klaytn directly.
        SendOptions sendOptionsForExecution = new SendOptions();
        sendOptionsForExecution.setFrom(keyring.getAddress());
        sendOptionsForExecution.setGas(BigInteger.valueOf(1000000));
        sendOptionsForExecution.setFeeDelegation(true);
        sendOptionsForExecution.setFeePayer(keyring.getAddress());
        sendOptionsForExecution.setFeeRatio(BigInteger.valueOf(50)); // Without feeRatio, `send` will use FeeDelegatedSmartContractExecution
        TransactionReceipt.TransactionReceiptData receiptData = contract.send(sendOptionsForExecution, "set", "key_inserted", "value_inserted");
        System.out.println(objectToString(receiptData));
    }*/


}
