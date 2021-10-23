package com.benyq.guochatapi.orm.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginEntity {
    private String phone;
    private String nick;
    private String id;
    private String uid;
    private String avatar;
    private int gender;
    private String token;

}
