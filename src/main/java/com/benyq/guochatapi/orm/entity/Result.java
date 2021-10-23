package com.benyq.guochatapi.orm.entity;


import com.benyq.guochatapi.base.error.ErrorCode;
import lombok.Data;

@Data
public class Result<T> {
    protected String errorMsg;
    protected int errorCode;
    protected T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.errorCode = 0;
        result.data = data;
        result.errorMsg = null;
        return result;
    }

    public static <T> Result<T> error(int errorCode, String errorMsg) {
        Result<T> result = new Result<>();
        result.errorCode = errorCode;
        result.data = null;
        result.errorMsg = errorMsg;
        return result;
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        Result<T> result = new Result<>();
        result.errorCode = errorCode.getErrorCode();
        result.data = null;
        result.errorMsg = errorCode.getErrorMsg();
        return result;
    }
}
