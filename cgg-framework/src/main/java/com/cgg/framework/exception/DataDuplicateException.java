package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

public class DataDuplicateException extends BizException {

    public DataDuplicateException() {
        super(PredefinedCode.DATA_DUPLICATE.getCode(), PredefinedCode.DATA_DUPLICATE.getMsg());
    }

    public DataDuplicateException(String retInfo) {
        super(PredefinedCode.DATA_DUPLICATE.getCode(), retInfo);
    }

}
