package com.benyq.guochatapi.controller;

import com.alibaba.fastjson.JSON;
import com.benyq.guochatapi.base.annotation.ApiMethod;
import com.benyq.guochatapi.base.websocket.WebSocketServer;
import com.benyq.guochatapi.orm.entity.MessageEntity;
import com.benyq.guochatapi.orm.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author benyq
 * @time 2021/10/23
 * @e-mail 1520063035@qq.com
 * @note 聊天记录上传过渡，不保存
 */

@RestController
@RequestMapping("/chat/")
public class ChatController {

    @Autowired
    WebSocketServer webSocketServer;

    /**
     *
     * @param id 发送者id
     * @param toId  接收者id
     * @param msg
     * @return
     */
    @PostMapping("send-message")
    @ApiMethod("发送消息")
    public Result<String> sendMessage(@RequestAttribute("id") String id, @RequestParam("to") String toId, @RequestParam("msg") String msg) {
        String chatId = "chat-" + toId;
        MessageEntity entity = new MessageEntity();
        entity.setSendTime(System.currentTimeMillis());
        entity.setMsg(msg);
        entity.setFromId(id);
        entity.setToId(toId);
        webSocketServer.sendMessage(chatId, JSON.toJSONString(entity));
        return Result.success("success");
    }

}
