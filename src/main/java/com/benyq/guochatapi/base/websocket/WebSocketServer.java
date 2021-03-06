package com.benyq.guochatapi.base.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author benyq
 * @date 2021/10/25
 * @email 1520063035@qq.com
 */
@ServerEndpoint(value = "/socket")
@Component
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger online = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    // uid 格式 chat-11
    private static Map<String,Session> sessionPools = new ConcurrentHashMap<>();

    //消息记录的缓存，暂时保存在内存中
    private static Map<String, List<String>> chatMessagePools = new ConcurrentHashMap<>();

    /**
     * 发送消息方法
     * @param session 客户端与socket建立的会话
     * @param message 消息
     * @throws IOException
     */
    public void sendMessage(Session session, String message) throws IOException{
        if(session != null){
            session.getBasicRemote().sendText(message);
        }
    }


    /**
     * 连接建立成功调用
     * @param session 客户端与socket建立的会话
     */
    @OnOpen
    public void onOpen(Session session){
    }

    /**
     * 关闭连接时调用
     */
    @OnClose
    public void onClose(Session session){
        String userName = session.getId();
        sessionPools.remove(userName);
        subOnlineCount();
        System.out.println(userName + "断开webSocket连接！当前人数为" + online);
    }

    /**
     * 收到客户端消息时触发（群发）
     * @param message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException{

        System.out.println("session: " + session.getId());
        System.out.println("message: " + message);

        if (message == null) {
            return;
        }

        WSMessage wsMessage = JSON.parseObject(message, WSMessage.class);
        if (wsMessage.getType() == WSMessage.TYPE_HEART) {
            System.out.println("message: 心跳包");
        }else if (wsMessage.getType() == WSMessage.TYPE_UID) {
            sessionPools.put(wsMessage.getData(), session);
            addOnlineCount();
            sendMessage(session, "chat: " + wsMessage.getData());

            //发送缓存消息
            List<String> chatMessages = chatMessagePools.get(wsMessage.getData());
            if (chatMessages != null) {
                chatMessages.forEach(msg -> {
                    sendMessage(wsMessage.getData(), msg);
                });
                chatMessagePools.remove(wsMessage.getData());
            }

            System.out.println(session.getId() + "加入webSocket！当前人数为" + online);
        }

    }

    /**
     * 发生错误时候
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    /**
     * 给指定用户发送消息
     * @param uid 用户名
     * @param message 消息
     * @throws IOException
     */
    public void sendMessage(String uid, String message){
        Session session = sessionPools.get(uid);

        //此时对方还未上线，需要缓存聊天记录
        if (session == null) {
            List<String> chatMessages = chatMessagePools.get(uid);
            if (chatMessages == null) {
                chatMessages = new ArrayList<>();
            }
            chatMessages.add(message);
            return;
        }

        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addOnlineCount(){
        online.incrementAndGet();
    }

    public static void subOnlineCount() {
        online.decrementAndGet();
    }

}
