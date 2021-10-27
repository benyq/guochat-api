package com.benyq.guochatapi.orm.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author benyq
 * @time 2021/10/27
 * @e-mail 1520063035@qq.com
 * @note 用户信息
 */
@Data
public class UserEntity {
    private String phone;
    private String nick;
    private String id;
    private String chatNo;
    private String avatar;
    private int gender;
    private String token;

}
