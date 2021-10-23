package com.benyq.guochatapi.base.error;

public enum ErrorCode {
    ERROR_LOGIN("账号或密码错误", 1),
    ERROR_TOKEN("token已失效，请重新登陆", 2),
    EXIST_USER("账号已存在，请登陆", 3),
    NOT_EXIST_SHOP("店铺不存在", 4),
    REPETITIVE_SHOP_NAME("店铺名称重复", 5),
    EXIST_SHOP_COUNT("存在店铺数量已达上线", 6),
    REPETITIVE_CATEGORY_NAME("货物分类名称重复", 7),
    UPLOAD_EMPTY_FILE("上传空文件", 8);






















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
