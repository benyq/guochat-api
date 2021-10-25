package com.benyq.guochatapi.controller;

import com.benyq.guochatapi.base.annotation.ApiMethod;
import com.benyq.guochatapi.orm.entity.LoginEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.orm.param.RegisterParam;
import com.benyq.guochatapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("login")
    @ApiMethod("登陆")
    public Result<LoginEntity> login(@RequestParam("phone") String phone, @RequestParam("pwd") String pwd) {
        return userService.login(phone, pwd);
    }

    @PostMapping("register")
    @ApiMethod("注册")
    public Result<LoginEntity> register(@Valid @RequestBody RegisterParam param) {
        return userService.register(param);
    }

    @GetMapping("info")
    @ApiMethod("用户信息")
    public String info(@RequestAttribute("id") String uid) {
        return "info-" + uid;
    }


    @PostMapping("edit-nick")
    @ApiMethod("修改昵称")
    public Result<String> editNick(@RequestAttribute("id") String id, @RequestParam("nick") String nick) {
        return userService.editNick(id, nick);
    }


    //虽然并不需要特意检查token，但还是开了一个接口意思一下
    @GetMapping("check-token")
    @ApiMethod("检查token")
    public Result<Boolean> checkoutToken() {
        return Result.success(true);
    }
}
