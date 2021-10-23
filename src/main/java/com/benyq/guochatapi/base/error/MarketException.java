package com.benyq.guochatapi.base.error;

public class MarketException extends Exception{
    private int errorCode;

    public MarketException(String message, int errorCode) {
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
