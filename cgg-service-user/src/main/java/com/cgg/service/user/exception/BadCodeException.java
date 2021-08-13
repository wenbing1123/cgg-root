package com.cgg.service.user.exception;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.exception.base.BaseAuthenticationException;
import lombok.Getter;

@Getter
public class BadCodeException extends BaseAuthenticationException {

    private static final PredefinedCode CODE = PredefinedCode.CODE_ERROR;

    public BadCodeException() {
        super(CODE.getCode(), CODE.getMsg());
    }

    public BadCodeException(String msg) {
        super(CODE.getCode(), msg);
    }

    public BadCodeException(AuthError authError) {
        super(CODE.getCode(), CODE.getMsg(), authError);
    }

    public BadCodeException(String msg, AuthError authError) {
        super(CODE.getCode(), msg, authError);
    }

    public BadCodeException(AuthError authError, Throwable cause) {
        super(CODE.getCode(), CODE.getMsg(), authError, cause);
    }

    public BadCodeException(String msg, AuthError authError, Throwable cause) {
        super(CODE.getCode(), msg, authError, cause);
    }


}
