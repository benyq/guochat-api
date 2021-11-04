package com.benyq.guochatapi.orm.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.beans.Transient;

/**
 * @author benyq
 * @time 2021/10/24
 * @e-mail 1520063035@qq.com
 * @note
 */
@Data
public class ContractEntity {
    private long id;
    private String uid;//联系人所有人id
    private String contractId;//联系人id
    private String chatNo;//联系人果聊号
    private String nick;
    private String remark;
    private int gender;
    private String avatar;
    @JSONField(serialize = false)
    private int status;
    private long createdTime;
}
