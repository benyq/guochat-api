package com.benyq.guochatapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开始数据库事务
@MapperScan("com.benyq.guochatapi.orm.dao") // 扫描Mybatis dao 文件
public class GuoChatApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuoChatApiApplication.class, args);
    }

}
