package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

public class DataExpiredException extends BizException {

    public DataExpiredException() {
        super(PredefinedCode.DATA_EXPIRED.getCode(), PredefinedCode.DATA_EXPIRED.getMsg());
    }

    public DataExpiredException(String retInfo) {
        super(PredefinedCode.DATA_EXPIRED.getCode(), retInfo);
    }

}
