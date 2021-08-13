package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

public class ArgumentException extends BizException {

    public ArgumentException() {
        super(PredefinedCode.ARGUMENT_ERROR.getCode(), PredefinedCode.ARGUMENT_ERROR.getMsg());
    }

    public ArgumentException(String retInfo) {
        super(PredefinedCode.ARGUMENT_ERROR.getCode(), retInfo);
    }

}
