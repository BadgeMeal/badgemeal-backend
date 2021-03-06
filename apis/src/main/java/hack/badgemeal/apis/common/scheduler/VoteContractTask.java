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

    @Value("${baobab-contract.vote-address}")
    private String voteConAddress;
    @Value("${baobab-contract.vote-abi}")
    private String voteConAbi;

    @Value("${baobab-contract.nft-address}")
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

            /*winnerName : 투표수가 높은 메뉴 조회 -> addWinnerProposal : 투표 결과 등록 -> 결과 확인후 DB저장 */
            String elected_menu = caver.callContractFuncView(voteConAddress, voteConAbi, "winnerName");
            System.out.println("elected_menu : " + elected_menu);
            String result = caver.callContractFunc(voteConAddress, voteConAbi, "addWinnerProposal", nftConAddress);
            System.out.println("addWinnerProposal : " + result);
            if(!result.contains("The proposal did not win majority of the votes.")){
                //db에 저장
                Menu menu = new Menu();
                menu.setType(elected_menu);
                menuRepository.save(menu);
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
