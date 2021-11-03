package com.benyq.guochatapi.service;

import com.benyq.guochatapi.base.error.ErrorCode;
import com.benyq.guochatapi.orm.dao.ContractDao;
import com.benyq.guochatapi.orm.entity.ContractEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.orm.param.ApplyContractParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.regex.Pattern;

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

    public Result<List<ContractEntity>> getAllContracts(String id) {
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
     *
     * @param contractId
     * @param status     0 拒绝， 1 同意
     * @return
     */
    @Transactional
    public Result<String> applyContractReply(String contractId, int status) {
        ContractEntity contractEntity = contractDao.queryContractById(contractId);

        if (contractEntity == null) {
            return Result.error(ErrorCode.APPLY_CONTRACT_NOT_EXISTS);
        }

        if (status == 0) {
            contractDao.applyContractRefuse(contractId);
        }else {
            contractDao.applyContractAgree(contractId);

            ApplyContractParam applyContractParam = new ApplyContractParam();
            applyContractParam.setUid(contractEntity.getContractId());
            applyContractParam.setApplyId(contractEntity.getUid());
            contractDao.applyContract(applyContractParam);
            contractDao.applyContractAgree(applyContractParam.getId().toString());
        }
        return Result.success("success");
    }


    public Result<ContractEntity> searchContract(String uid, String key) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        ContractEntity userContract = Pattern.matches(regex, key) ? contractDao.searchContractByPhone(key) : contractDao.searchContractByChatNo(key);
        if (userContract == null) {
            return Result.error(ErrorCode.USER_NOT_EXISTS);
        }
        ContractEntity contractEntity = contractDao.queryContract(uid, userContract.getContractId());
        if (contractEntity != null && contractEntity.getStatus() == 1) {
            userContract.setUid(contractEntity.getUid());
            userContract.setRemark(contractEntity.getRemark());
        }
        return Result.success(userContract);
    }

    public Result<ContractEntity> queryContractByCode(String uid, String chatId) {
        String id = chatId.substring(chatId.indexOf("chat-") + 5);
        System.out.println("chatId: " + id);
        ContractEntity userContract = contractDao.searchContractByChatId(id);
        if (userContract == null) {
            return Result.error(ErrorCode.USER_NOT_EXISTS);
        }

        ContractEntity contract = contractDao.queryContract(uid, id);
        System.out.println(contract);
        if (contract != null && contract.getStatus() == 1) {
            userContract.setRemark(contract.getRemark());
            userContract.setContractId(contract.getContractId());
            userContract.setUid(contract.getUid());
        }
        return Result.success(userContract);
    }
}
