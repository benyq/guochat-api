package com.benyq.guochatapi.controller;

import com.benyq.guochatapi.orm.entity.ContractEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public Result<List<ContractEntity>> getAllContracts(@PathVariable("id") String id){
        return contractService.getAllContracts(id);
    }

}
