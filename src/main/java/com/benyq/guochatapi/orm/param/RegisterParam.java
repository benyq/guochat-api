package com.benyq.guochatapi.orm.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
public class RegisterParam extends BaseAddParam {

    @NotNull(message = "密码不能为空")
    private String pwd;

    @NotNull(message = "手机号码不能为空")
    @Length(min = 11, max = 11, message = "请输入正确的手机号码")
    private String phone;


    private String nick;
    private String uid;
}
