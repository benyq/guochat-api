package com.benyq.guochatapi.orm.entity;

import lombok.Data;

/**
 * @author benyq
 * @time 2021/10/24
 * @e-mail 1520063035@qq.com
 * @note
 */
@Data
public class ContractEntity {
    private long id;
    private String uid;
    private String contractId;
    private String nick;
    private String remark;
    private int gender;
    private String avatar;
}
