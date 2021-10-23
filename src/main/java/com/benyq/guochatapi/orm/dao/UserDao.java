package com.benyq.guochatapi.orm.dao;

import com.benyq.guochatapi.orm.entity.LoginEntity;
import com.benyq.guochatapi.orm.param.RegisterParam;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    LoginEntity login(String phone, String pwd);

    void register(RegisterParam param);

    Long checkUserExist(String phone);
}
