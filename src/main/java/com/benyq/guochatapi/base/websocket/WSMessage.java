package com.benyq.guochatapi.base.websocket;

import lombok.Data;

/**
 * @author benyq
 * @time 2021/10/25
 * @e-mail 1520063035@qq.com
 * @note WebSocket 数据包
 */
@Data
public class WSMessage {


    public static int TYPE_HEART = 101;
    public static int TYPE_UID = 102;


    private int type;
    private String data;

}
