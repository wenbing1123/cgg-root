package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

public class DataNotFoundException extends BizException {

    public DataNotFoundException() {
        super(PredefinedCode.DATA_NOT_FOUND.getCode(), PredefinedCode.DATA_NOT_FOUND.getMsg());
    }

    public DataNotFoundException(String retInfo) {
        super(PredefinedCode.DATA_NOT_FOUND.getCode(), retInfo);
    }

}
