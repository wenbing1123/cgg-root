package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

/**
 * 系统异常
 */
public class SvcFailException extends BaseException {

    public SvcFailException() {
        super(PredefinedCode.SVC_ERROR.getCode(), PredefinedCode.SVC_ERROR.getMsg());
    }

    public SvcFailException(String retInfo) {
        super(PredefinedCode.SVC_ERROR.getCode(), retInfo);
    }

}
