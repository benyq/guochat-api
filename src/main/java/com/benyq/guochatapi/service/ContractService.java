package com.benyq.guochatapi.service;

import com.benyq.guochatapi.base.error.ErrorCode;
import com.benyq.guochatapi.orm.dao.ContractDao;
import com.benyq.guochatapi.orm.entity.ContractEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.orm.param.ApplyContractParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    public Result<String> applyContract(String id, String applyId) {
        //先检查是否已经加入联系人
        ContractEntity contractEntity = contractDao.queryContract(id, applyId);
        if (contractEntity != null) {
            if (contractEntity.getStatus() == 1) return Result.error(ErrorCode.CONTRACT_EXISTS);
            else return Result.success("success");
        }

        ApplyContractParam applyContractParam = new ApplyContractParam();
        applyContractParam.setUid(id);
        applyContractParam.setApplyId(applyId);
        long effectRows = contractDao.applyContract(applyContractParam);
        return Result.success("success");
    }


    /**
     * 联系人申请处理
     * @param contractId
     * @param status 0 拒绝， 1 同意
     * @return
     */
    public Result<String> applyContractReply(String contractId, int status) {
        ContractEntity contractEntity = contractDao.queryContractById(contractId);

        if (contractEntity == null) {
            return Result.error(ErrorCode.APPLY_CONTRACT_NOT_EXISTS);
        }

        long effectRows = status == 0 ? contractDao.applyContractRefuse(contractId) : contractDao.applyContractAgree(contractId);
        return Result.success("success");
    }

}
