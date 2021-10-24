package com.benyq.guochatapi.service;

import com.benyq.guochatapi.orm.dao.ContractDao;
import com.benyq.guochatapi.orm.entity.ContractEntity;
import com.benyq.guochatapi.orm.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author benyq
 * @time 2021/10/24
 * @e-mail 1520063035@qq.com
 * @note
 */

@Service
public class ContractService {
    @Autowired
    ContractDao contractDao;

    public Result<List<ContractEntity>> getAllContracts(String id){
        return Result.success(contractDao.getAllContracts(id));
    }

}
