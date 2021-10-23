package com.benyq.guochatapi.orm.entity;

import com.benyq.guochatapi.base.error.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PageResult<T> extends Result<T> {
    //全部页数
    private int pages;
    //全部总数
    private int total;

    public static <T> PageResult<T> error(int errorCode, String errorMsg) {
        PageResult<T> result = new PageResult<>();
        result.errorCode = errorCode;
        result.data = null;
        result.errorMsg = errorMsg;
        return result;
    }

    public static <T> PageResult<T> error(ErrorCode errorCode) {
        PageResult<T> result = new PageResult<>();
        result.errorCode = errorCode.getErrorCode();
        result.data = null;
        result.errorMsg = errorCode.getErrorMsg();
        return result;
    }

    public static <T> PageResult<T> success(T data, int page, int total) {
        PageResult<T> result = new PageResult<>();
        result.errorCode = 0;
        result.data = data;
        result.errorMsg = null;
        result.pages = page;
        result.total = total;
        return result;
    }
}
