package hack.badgemeal.apis.domain.vote.controller;

import hack.badgemeal.apis.common.dto.ResultDto;
import hack.badgemeal.apis.common.util.CaverJava;
import hack.badgemeal.apis.domain.menu.model.Menu;
import hack.badgemeal.apis.domain.menu.service.MenuService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"(해커톤 시연용) 매달 말일, 매달 1일에 실행되는 Controller입니다."})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("vote")
public class VoteController {
    private final MenuService menuService;

    @Value("${baobab-contract.vote-address}")
    private String voteConAddress;
    @Value("${baobab-contract.vote-abi}")
    private String voteConAbi;

    @Value("${baobab-contract.nft-address}")
    private String nftConAddress;

    /*매달 말일  실행 (end vote = start Proposal)*/
    @GetMapping("/endVote")
    public Object endVote() {
        ResultDto resultDto = new ResultDto();
        String status = "fail";
        try{
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
                menu.setKeyword(elected_menu);
                menu.setKeyword(elected_menu);
                menuService.save(menu);
                status =  "success";
            }
        }catch (Exception e){
            e.printStackTrace();
            status =  "fail";
            resultDto.setMsg(e.getMessage());
        }

        resultDto.setStatus(status);
        return resultDto;
    }

    /*매달 1일 setVoteStartTime 실행*/
    @GetMapping("/voteStart")
    public Object endVoteAndStartProposal() {
        ResultDto resultDto = new ResultDto();
        String status = "fail";
        try{
            CaverJava caver = new CaverJava();
            caver.callContractFunc(voteConAddress, voteConAbi, "setVoteStartTime");
            status = "success";
        }catch (Exception e){
            e.printStackTrace();
            status =  "fail";
            resultDto.setMsg(e.getMessage());
        }

        resultDto.setStatus(status);
        return resultDto;

    }
}
