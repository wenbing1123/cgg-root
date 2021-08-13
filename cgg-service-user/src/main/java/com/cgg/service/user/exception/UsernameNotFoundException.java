package com.cgg.service.user.exception;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.exception.base.BaseAuthenticationException;
import lombok.Getter;

@Getter
public class UsernameNotFoundException extends BaseAuthenticationException {

    private static final PredefinedCode CODE = PredefinedCode.USER_NOT_EXISTS;

    public UsernameNotFoundException() {
        super(CODE.getCode(), CODE.getMsg());
    }

    public UsernameNotFoundException(String msg) {
        super(CODE.getCode(), msg);
    }

    public UsernameNotFoundException(AuthError authError) {
        super(CODE.getCode(), CODE.getMsg(), authError);
    }

    public UsernameNotFoundException(String msg, AuthError authError) {
        super(CODE.getCode(), msg, authError);
    }

    public UsernameNotFoundException(AuthError authError, Throwable cause) {
        super(CODE.getCode(), CODE.getMsg(), authError, cause);
    }

    public UsernameNotFoundException(String msg, AuthError authError, Throwable cause) {
        super(CODE.getCode(), msg, authError, cause);
    }


}
