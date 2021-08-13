package com.cgg.framework.exception;

public abstract class BaseException extends RuntimeException{

    protected String retCode;
    protected String retInfo;

    public BaseException(String retCode, String retInfo) {
        super(String.format("%s[code:%s]", retInfo, retCode));
        this.retCode = retCode;
        this.retInfo = retInfo;
    }

    public String getRetCode() {
        return retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }
}
