package com.benyq.guochatapi.controller;

import com.benyq.guochatapi.base.annotation.ApiMethod;
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
}
