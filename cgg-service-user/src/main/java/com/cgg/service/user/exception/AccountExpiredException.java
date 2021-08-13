package com.cgg.service.user.exception;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.exception.base.BaseAuthenticationException;
import lombok.Getter;

@Getter
public class AccountExpiredException extends BaseAuthenticationException {

    private static final PredefinedCode CODE = PredefinedCode.USER_EXPIRED;

    public AccountExpiredException() {
        super(CODE.getCode(), CODE.getMsg());
    }

    public AccountExpiredException(AuthError authError) {
        super(CODE.getCode(), CODE.getMsg(), authError);
    }

    public AccountExpiredException(AuthError authError, Throwable cause) {
        super(CODE.getCode(), CODE.getMsg(), authError, cause);
    }
}
