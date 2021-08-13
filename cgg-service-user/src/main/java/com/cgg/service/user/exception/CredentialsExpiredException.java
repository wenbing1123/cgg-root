package com.cgg.service.user.exception;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.exception.base.BaseAuthenticationException;
import lombok.Getter;

@Getter
public class CredentialsExpiredException extends BaseAuthenticationException {

    private static final PredefinedCode CODE = PredefinedCode.USER_PASSWORD_EXPIRED;

    public CredentialsExpiredException() {
        super(CODE.getCode(), CODE.getMsg());
    }

    public CredentialsExpiredException(AuthError authError) {
        super(CODE.getCode(), CODE.getMsg(), authError);
    }

    public CredentialsExpiredException(AuthError authError, Throwable cause) {
        super(CODE.getCode(), CODE.getMsg(), authError, cause);
    }
}
