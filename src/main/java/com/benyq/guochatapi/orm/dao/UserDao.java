package com.benyq.guochatapi.orm.dao;

import com.benyq.guochatapi.orm.entity.UserEntity;
import com.benyq.guochatapi.orm.param.RegisterParam;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    UserEntity login(String phone, String pwd);

    void register(RegisterParam param);

    Long checkUserExist(String phone);

    Long editNick(String id, String nick, long updateTime);

    Long editAvatar(String id, long fileId, long updateTime);

}
