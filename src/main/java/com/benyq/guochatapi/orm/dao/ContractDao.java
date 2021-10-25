package com.benyq.guochatapi.orm.dao;

import com.benyq.guochatapi.orm.entity.ContractEntity;
import com.benyq.guochatapi.orm.param.ApplyContractParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author benyq
 * @time 2021/10/24
 * @e-mail 1520063035@qq.com
 * @note
 */
@Repository
public interface ContractDao {

    List<ContractEntity> getAllContracts(String id);

    long applyContract(ApplyContractParam applyContractParam);

    ContractEntity queryContract(String id, String applyId);

    ContractEntity queryContractById(String contractId);

    long applyContractAgree(String id);
    long applyContractRefuse(String id);
}
