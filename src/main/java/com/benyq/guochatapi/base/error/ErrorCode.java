package com.benyq.guochatapi.base.error;

public enum  ErrorCode {
    ERROR_LOGIN("账号或密码错误", 1),
    ERROR_TOKEN("token已失效，请重新登陆", 2),
    EXIST_USER("账号已存在，请登陆", 3),
    EDIT_NICK_ERROR("修改昵称失败", 4),
    UPLOAD_EMPTY_FILE("上传空文件", 6);






















    ErrorCode(String errorMsg, int errorCode) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    private String errorMsg;
    private int errorCode;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
