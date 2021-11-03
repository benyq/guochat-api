package com.benyq.guochatapi.controller;

import com.alibaba.fastjson.JSON;
import com.benyq.guochatapi.base.annotation.ApiMethod;
import com.benyq.guochatapi.base.websocket.WebSocketServer;
import com.benyq.guochatapi.orm.entity.MessageEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

    @Autowired
    FileService fileService;

    /**
     *
     * @param id 发送者id
     * @param toId  接收者id
     * @param msg
     * @return
     */
    @PostMapping("send-message")
    @ApiMethod("发送消息")
    public Result<Boolean> sendChatMessage(@RequestAttribute("id") String id, @RequestParam("to") String toId, @RequestParam("msg") String msg) {
        String chatId = "chat-" + toId;
        MessageEntity entity = new MessageEntity();
        entity.setSendTime(System.currentTimeMillis());
        entity.setMsg(msg);
        entity.setFromId(id);
        entity.setToId(toId);
        entity.setType(1);
        webSocketServer.sendMessage(chatId, JSON.toJSONString(entity));
        return Result.success(true);
    }


    @ApiMethod("发送图片、视频以及其他文件")
    @PostMapping("send-chat-file")
    public Result<Boolean> sendChatFile(@RequestAttribute("id") String id, @RequestParam("to") String toId, @RequestParam("type") int type, MultipartHttpServletRequest multiReq) {
        //先保存文件
        Result<String> result = fileService.uploadChatFile(multiReq, type);
        //错误
        if (result.getErrorCode() != 0) {
            return Result.error(result.getErrorCode(), result.getErrorMsg());
        }

        MessageEntity entity = new MessageEntity();
        entity.setSendTime(System.currentTimeMillis());
        entity.setType(type);
        entity.setFromId(id);
        entity.setToId(toId);
        entity.setMsg(result.getData());
        String chatId = "chat-" + toId;
        webSocketServer.sendMessage(chatId, JSON.toJSONString(entity));
        return Result.success(true);
    }

}
