package com.benyq.guochatapi.service;

import com.benyq.guochatapi.base.error.ErrorCode;
import com.benyq.guochatapi.base.interceptor.JwtConfig;
import com.benyq.guochatapi.orm.dao.UserDao;
import com.benyq.guochatapi.orm.entity.LoginEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.orm.param.RegisterParam;
import com.benyq.guochatapi.utils.DateUtil;
import com.benyq.guochatapi.utils.MD5Util;
import com.benyq.guochatapi.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    JwtConfig jwtConfig;

    public Result<LoginEntity> login(String phone, String pwd) {
        LoginEntity loginEntity = userDao.login(phone, UserUtil.saltEncryptionPwd(pwd));
        if (loginEntity == null) {
            return Result.error(ErrorCode.ERROR_LOGIN);
        }
        String token = jwtConfig.getToken(loginEntity.getId());
        loginEntity.setToken(token);
        return Result.success(loginEntity);
    }

    @Transactional
    public Result<LoginEntity> register(RegisterParam param){
        Long existUid = userDao.checkUserExist(param.getPhone());
        if (existUid != null) {
            //已存在
            return Result.error(ErrorCode.EXIST_USER);
        }
        param.setNick("果聊用户_" + param.getPhone());
        param.setUid("chat_id_" + DateUtil.dateToHexString());
        param.setPwd(UserUtil.saltEncryptionPwd(param.getPwd()));
        userDao.register(param);
        LoginEntity entity = new LoginEntity();
        entity.setId(String.valueOf(param.getId()));
        entity.setUid(param.getUid());
        entity.setToken(jwtConfig.getToken(entity.getUid()));
        entity.setPhone(param.getPhone());
        entity.setNick(param.getNick());
        return Result.success(entity);
    }

    public Result<String> editNick(String uid, String nick) {
        long effectRows = userDao.editNick(uid, nick, System.currentTimeMillis());
        return effectRows > 0 ? Result.success("success") : Result.error(ErrorCode.EDIT_NICK_ERROR);
    }

}
