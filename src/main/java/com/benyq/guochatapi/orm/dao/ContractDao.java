package com.benyq.guochatapi.orm.dao;

import com.benyq.guochatapi.orm.entity.ContractEntity;
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
}
