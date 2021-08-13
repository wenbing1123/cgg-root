package com.cgg.service.user.exception;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.exception.base.BaseAuthenticationException;
import lombok.Getter;

@Getter
public class BadCredentialsException extends BaseAuthenticationException {

    private static final PredefinedCode CODE = PredefinedCode.USERNAME_PASSWORD_ERROR;

    public BadCredentialsException() {
        super(CODE.getCode(), CODE.getMsg());
    }

    public BadCredentialsException(AuthError authError) {
        super(CODE.getCode(), CODE.getMsg(), authError);
    }

    public BadCredentialsException(AuthError authError, Throwable cause) {
        super(CODE.getCode(), CODE.getMsg(), authError, cause);
    }
}
