package com.benyq.guochatapi.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author benyq
 * @date 2021/10/27
 * @email 1520063035@qq.com
 */
@Component
public class ServerConfig {

    @Value("${server.port}")
    private int serverPort;

    public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://" + address.getHostAddress() + "/";
    }

}
