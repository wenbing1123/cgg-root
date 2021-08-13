package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

/**
 * 系统异常
 */
public class SvcTimeoutException extends BaseException {

    public SvcTimeoutException() {
        super(PredefinedCode.SVC_TIMEOUT.getCode(), PredefinedCode.SVC_TIMEOUT.getMsg());
    }

    public SvcTimeoutException(String retInfo) {
        super(PredefinedCode.SVC_TIMEOUT.getCode(), retInfo);
    }

}
