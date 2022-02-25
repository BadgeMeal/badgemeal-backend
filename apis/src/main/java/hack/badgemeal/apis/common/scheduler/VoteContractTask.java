package hack.badgemeal.apis.common.scheduler;

import hack.badgemeal.apis.common.util.CaverJava;
import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class VoteContractTask {
    private final MenuRepository menuRepository;

    @Value("${cypress-contract.vote-address}")
    private String voteConAddress;
    @Value("${cypress-contract.vote-abi}")
    private String voteConAbi;

    @Value("${cypress-contract.nft-address}")
    private String nftConAddress;

    /*매달 말일  실행
    @Scheduled(cron = "0 0 0 28-31 * *")*/
    @Scheduled(cron = "0 0 0 2,4,6,8,10,12,14,16,18,20,22,24,26,28,30 * *")
    public void proposeStart() throws TransactionException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Calendar cal = Calendar.getInstance();
        if(cal.getTime().getDate() == cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
            /*setProposeStartTime : 투표 종료 및 제안 시작*/
            CaverJava caver = new CaverJava();
            caver.callContractFunc(voteConAddress, voteConAbi, "setProposeStartTime");

            try{
                /*winnerName : 투표수가 높은 메뉴 조회 -> addWinnerProposal : 투표 결과 등록 -> 결과 확인후 DB저장 */
                String elected_menu = caver.callContractFuncView("0x1faF6bcfC3557041027591b3737DDD548e5B1771", "[ { \"constant\": true, \"inputs\": [ { \"name\": \"\", \"type\": \"uint256\" } ], \"name\": \"proposals\", \"outputs\": [ { \"name\": \"name\", \"type\": \"string\" }, { \"name\": \"voteCount\", \"type\": \"uint256\" }, { \"name\": \"proposer\", \"type\": \"address\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [ { \"name\": \"_proposal\", \"type\": \"uint256\" }, { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"vote\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [ { \"name\": \"_name\", \"type\": \"string\" }, { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"proposeMenu\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [], \"name\": \"setProposeStartTime\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"voteStartTime\", \"outputs\": [ { \"name\": \"\", \"type\": \"uint256\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [ { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"addWinnerProposal\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"winningProposal\", \"outputs\": [ { \"name\": \"winningProposal_\", \"type\": \"uint256\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [], \"name\": \"renounceOwnership\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [], \"name\": \"setVoteStartTime\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"owner\", \"outputs\": [ { \"name\": \"\", \"type\": \"address\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"isOwner\", \"outputs\": [ { \"name\": \"\", \"type\": \"bool\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [ { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"isNFTholder\", \"outputs\": [ { \"name\": \"\", \"type\": \"bool\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [ { \"name\": \"\", \"type\": \"uint256\" } ], \"name\": \"winnerProposals\", \"outputs\": [ { \"name\": \"name\", \"type\": \"string\" }, { \"name\": \"voteCount\", \"type\": \"uint256\" }, { \"name\": \"proposer\", \"type\": \"address\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"proposeStartTime\", \"outputs\": [ { \"name\": \"\", \"type\": \"uint256\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"winnerName\", \"outputs\": [ { \"name\": \"winnerName_\", \"type\": \"string\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [ { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"isMasterNFTholder\", \"outputs\": [ { \"name\": \"\", \"type\": \"bool\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [ { \"name\": \"newOwner\", \"type\": \"address\" } ], \"name\": \"transferOwnership\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"anonymous\": false, \"inputs\": [ { \"indexed\": true, \"name\": \"name\", \"type\": \"string\" }, { \"indexed\": true, \"name\": \"voteCount\", \"type\": \"uint256\" }, { \"indexed\": false, \"name\": \"proposer\", \"type\": \"address\" } ], \"name\": \"AddWinner\", \"type\": \"event\" }, { \"anonymous\": false, \"inputs\": [ { \"indexed\": true, \"name\": \"previousOwner\", \"type\": \"address\" }, { \"indexed\": true, \"name\": \"newOwner\", \"type\": \"address\" } ], \"name\": \"OwnershipTransferred\", \"type\": \"event\" } ]", "winnerName");
                System.out.println("elected_menu : " + elected_menu);
                String result = caver.callContractFunc("0x1faF6bcfC3557041027591b3737DDD548e5B1771", "[ { \"constant\": true, \"inputs\": [ { \"name\": \"\", \"type\": \"uint256\" } ], \"name\": \"proposals\", \"outputs\": [ { \"name\": \"name\", \"type\": \"string\" }, { \"name\": \"voteCount\", \"type\": \"uint256\" }, { \"name\": \"proposer\", \"type\": \"address\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [ { \"name\": \"_proposal\", \"type\": \"uint256\" }, { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"vote\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [ { \"name\": \"_name\", \"type\": \"string\" }, { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"proposeMenu\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [], \"name\": \"setProposeStartTime\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"voteStartTime\", \"outputs\": [ { \"name\": \"\", \"type\": \"uint256\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [ { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"addWinnerProposal\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"winningProposal\", \"outputs\": [ { \"name\": \"winningProposal_\", \"type\": \"uint256\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [], \"name\": \"renounceOwnership\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [], \"name\": \"setVoteStartTime\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"owner\", \"outputs\": [ { \"name\": \"\", \"type\": \"address\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"isOwner\", \"outputs\": [ { \"name\": \"\", \"type\": \"bool\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [ { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"isNFTholder\", \"outputs\": [ { \"name\": \"\", \"type\": \"bool\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [ { \"name\": \"\", \"type\": \"uint256\" } ], \"name\": \"winnerProposals\", \"outputs\": [ { \"name\": \"name\", \"type\": \"string\" }, { \"name\": \"voteCount\", \"type\": \"uint256\" }, { \"name\": \"proposer\", \"type\": \"address\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"proposeStartTime\", \"outputs\": [ { \"name\": \"\", \"type\": \"uint256\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [], \"name\": \"winnerName\", \"outputs\": [ { \"name\": \"winnerName_\", \"type\": \"string\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": true, \"inputs\": [ { \"name\": \"_nftAddress\", \"type\": \"address\" } ], \"name\": \"isMasterNFTholder\", \"outputs\": [ { \"name\": \"\", \"type\": \"bool\" } ], \"payable\": false, \"stateMutability\": \"view\", \"type\": \"function\" }, { \"constant\": false, \"inputs\": [ { \"name\": \"newOwner\", \"type\": \"address\" } ], \"name\": \"transferOwnership\", \"outputs\": [], \"payable\": false, \"stateMutability\": \"nonpayable\", \"type\": \"function\" }, { \"anonymous\": false, \"inputs\": [ { \"indexed\": true, \"name\": \"name\", \"type\": \"string\" }, { \"indexed\": true, \"name\": \"voteCount\", \"type\": \"uint256\" }, { \"indexed\": false, \"name\": \"proposer\", \"type\": \"address\" } ], \"name\": \"AddWinner\", \"type\": \"event\" }, { \"anonymous\": false, \"inputs\": [ { \"indexed\": true, \"name\": \"previousOwner\", \"type\": \"address\" }, { \"indexed\": true, \"name\": \"newOwner\", \"type\": \"address\" } ], \"name\": \"OwnershipTransferred\", \"type\": \"event\" } ]", "addWinnerProposal", "0x510f5F91aAec4f545dd59c36A59434Aa1982B3DA");
                System.out.println("addWinnerProposal : " + result);

                if(!result.contains("The proposal did not win majority of the votes.")){
                    //db에 저장
                    Menu menu = new Menu();
                    menu.setType(elected_menu);
                    menuRepository.save(menu);
                }
            }catch (IOException e){
                // 종료할 조건이 안되는 경우
                System.out.println("이번 회차 투표는 선정된 메뉴가 없습니다.");
            }
        }
    }

    /*매달 1일 setVoteStartTime 실행
    @Scheduled(cron = "0 0 0 1 * *")*/
    @Scheduled(cron = "0 0 0 1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31 * *")
    public void voteStart() throws TransactionException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        CaverJava caver = new CaverJava();
        caver.callContractFunc(voteConAddress, voteConAbi, "setVoteStartTime");
    }
}
