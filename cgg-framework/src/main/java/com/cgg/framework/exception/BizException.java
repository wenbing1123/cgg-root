package com.cgg.framework.exception;

/**
 * 业务异常
 */
public abstract class BizException extends BaseException {

    public BizException(String retCode, String retInfo) {
        super(retCode, retInfo);
    }

}
