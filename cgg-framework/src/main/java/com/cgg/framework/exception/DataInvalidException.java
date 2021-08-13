package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

public class DataInvalidException extends BizException {

    public DataInvalidException() {
        super(PredefinedCode.DATA_INVALID.getCode(), PredefinedCode.DATA_INVALID.getMsg());
    }

    public DataInvalidException(String retInfo) {
        super(PredefinedCode.DATA_INVALID.getCode(), retInfo);
    }

}
