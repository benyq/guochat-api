package com.benyq.guochatapi.controller;

import com.benyq.guochatapi.base.annotation.ApiMethod;
import com.benyq.guochatapi.orm.entity.UserEntity;
import com.benyq.guochatapi.orm.entity.Result;
import com.benyq.guochatapi.orm.param.RegisterParam;
import com.benyq.guochatapi.service.FileService;
import com.benyq.guochatapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    @PostMapping("login")
    @ApiMethod("登陆")
    public Result<UserEntity> login(@RequestParam("phone") String phone, @RequestParam("pwd") String pwd) {
        return userService.login(phone, pwd);
    }

    @PostMapping("register")
    @ApiMethod("注册")
    public Result<UserEntity> register(@Valid @RequestBody RegisterParam param) {
        return userService.register(param);
    }

    @GetMapping("info")
    @ApiMethod("用户信息")
    public String info(@RequestParam("id") String uid) {
        return "info-";
    }


    @PostMapping("edit-nick")
    @ApiMethod("修改昵称")
    public Result<String> editNick(@RequestAttribute("id") String id, @RequestParam("nick") String nick) {
        return userService.editNick(id, nick);
    }


    @ApiMethod("上传头像")
    @PostMapping("upload-avatar")
    public Result<String> uploadAvatar(@RequestAttribute("id") String id,
                                               MultipartHttpServletRequest multiReq) {
        return fileService.uploadAvatar(id, multiReq);
    }

    //虽然并不需要特意检查token，但还是开了一个接口意思一下
    @GetMapping("check-token")
    @ApiMethod("检查token")
    public Result<Boolean> checkoutToken() {
        return Result.success(true);
    }
}
