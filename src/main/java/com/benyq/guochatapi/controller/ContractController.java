package com.benyq.guochatapi.controller;

import com.benyq.guochatapi.base.annotation.ApiMethod;
import com.benyq.guochatapi.base.error.ErrorCode;
import com.benyq.guochatapi.orm.entity.ContractEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author benyq
 * @time 2021/10/24
 * @e-mail 1520063035@qq.com
 * @note
 */
@RestController
@RequestMapping("/contract/")
public class ContractController {

    @Autowired
    ContractService contractService;

    @GetMapping("get-all-contract")
    @ApiMethod("获取所有联系人")
    public Result<List<ContractEntity>> getAllContracts(@RequestAttribute("id") String id){
        return contractService.getAllContracts(id);
    }

    /**
     * 搜索联系人
     * @param key 可以是 手机号，也可以 果聊id
     * @return
     */
    @GetMapping("search-contract")
    @ApiMethod("搜索联系人")
    public Result<ContractEntity> searchContract(@RequestAttribute("id") String id, @RequestParam("key") String key) {
        return contractService.searchContract(id, key);
    }

    @PostMapping("apply-contract")
    @ApiMethod("联系人申请")
    public Result<String> applyContract(@RequestAttribute("id") String id, @RequestParam("apply_id") String applyId) {
        return contractService.applyContract(id, applyId);
    }

    @PostMapping("agree-contract")
    @ApiMethod("联系人申请通过")
    public Result<String> agreeContract(@RequestParam("contract_id") String contractId) {
        return contractService.applyContractReply(contractId, 1);
    }

    @PostMapping("refuse-contract")
    @ApiMethod("联系人申请拒绝")
    public Result<String> refuseContract(@RequestParam("contract_id") String contractId) {
        return contractService.applyContractReply(contractId, 0);
    }

    /**
     *
     * @param chatId 例如 chat-11
     * @return
     */
    @GetMapping("code-contract")
    @ApiMethod("扫码查询联系人")
    public Result<ContractEntity> queryContractByCode(@RequestAttribute("id") String id, @RequestParam("chat-id") String chatId) {
        if (!chatId.contains("chat-")) {
            return Result.error(ErrorCode.PARAMETER_FORMAT_ERROR);
        }
        return contractService.queryContractByCode(id, chatId);
    }

    @GetMapping("app-contract-record")
    @ApiMethod("获取联系人申请记录，包括自己申请以及别人向自己申请")
    public Result<List<ContractEntity>> getApplyContractRecord(@RequestAttribute("id") String id) {
        return contractService.getApplyContractRecord(id);
    }
}
