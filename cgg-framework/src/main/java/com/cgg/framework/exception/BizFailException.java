package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

public class BizFailException extends BizException {

    public BizFailException() {
        super(PredefinedCode.BIZ_FAIL.getCode(), PredefinedCode.BIZ_FAIL.getMsg());
    }

    public BizFailException(String retInfo) {
        super(PredefinedCode.BIZ_FAIL.getCode(), retInfo);
    }

}
