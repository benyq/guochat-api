package com.benyq.guochatapi.base.error;

public class ChatException extends Exception{
    private int errorCode;

    public ChatException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
