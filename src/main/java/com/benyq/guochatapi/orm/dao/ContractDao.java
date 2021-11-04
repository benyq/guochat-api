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

    //这两个方法单纯只是查询t_contract表
    ContractEntity queryContract(String id, String applyId);
    ContractEntity queryContractById(String contractId);

    long applyContractAgree(String id);
    long applyContractRefuse(String id);

    ContractEntity searchContractByPhone(String phone);
    ContractEntity searchContractByChatNo(String chatNo);
    ContractEntity searchContractByChatId(String id);

    //主动向别人申请
    List<ContractEntity> getApplyContractRecord(String uid);
    //别人向自己申请
    List<ContractEntity> getBeApplyContractRecord(String uid);
}
