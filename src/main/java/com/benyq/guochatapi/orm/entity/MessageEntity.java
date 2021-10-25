package com.benyq.guochatapi.orm.entity;

import lombok.Data;

/**
 * @author benyq
 * @time 2021/10/25
 * @e-mail 1520063035@qq.com
 * @note
 */
@Data
public class MessageEntity {
    private String fromId;
    private String toId;
    private String msg;
    private long sendTime;
}
