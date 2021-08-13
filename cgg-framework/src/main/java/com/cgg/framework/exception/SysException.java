package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

/**
 * 系统异常
 */
public class SysException extends BaseException {

    public SysException() {
        super(PredefinedCode.ERROR.getCode(), PredefinedCode.ERROR.getMsg());
    }

    public SysException(String retInfo) {
        super(PredefinedCode.ERROR.getCode(), retInfo);
    }

}
