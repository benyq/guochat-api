package com.benyq.guochatapi.service;

import com.benyq.guochatapi.base.error.ErrorCode;
import com.benyq.guochatapi.base.interceptor.JwtConfig;
import com.benyq.guochatapi.orm.dao.UserDao;
import com.benyq.guochatapi.orm.entity.ContractEntity;
import com.benyq.guochatapi.orm.entity.UserEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.orm.param.RegisterParam;
import com.benyq.guochatapi.utils.DateUtil;
import com.benyq.guochatapi.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    JwtConfig jwtConfig;

    public Result<UserEntity> login(String phone, String pwd) {
        UserEntity loginEntity = userDao.login(phone, UserUtil.saltEncryptionPwd(pwd));
        if (loginEntity == null) {
            return Result.error(ErrorCode.ERROR_LOGIN);
        }
        String token = jwtConfig.getToken(loginEntity.getId());
        loginEntity.setToken(token);
        return Result.success(loginEntity);
    }

    @Transactional
    public Result<UserEntity> register(RegisterParam param){
        Long existUid = userDao.checkUserExist(param.getPhone());
        if (existUid != null) {
            //已存在
            return Result.error(ErrorCode.EXIST_USER);
        }
        param.setNick("果聊用户_" + param.getPhone());
        param.setUid("chat_id_" + DateUtil.dateToHexString());
        param.setPwd(UserUtil.saltEncryptionPwd(param.getPwd()));
        userDao.register(param);
        UserEntity entity = new UserEntity();
        entity.setId(String.valueOf(param.getId()));
        entity.setChatNo(param.getUid());
        entity.setToken(jwtConfig.getToken(entity.getId()));
        entity.setPhone(param.getPhone());
        entity.setNick(param.getNick());
        return Result.success(entity);
    }

    public Result<String> editNick(String uid, String nick) {
        long effectRows = userDao.editNick(uid, nick, System.currentTimeMillis());
        return effectRows > 0 ? Result.success("success") : Result.error(ErrorCode.EDIT_NICK_ERROR);
    }

}
