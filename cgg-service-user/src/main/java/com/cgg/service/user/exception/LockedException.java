package com.cgg.service.user.exception;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.exception.base.BaseAuthenticationException;
import lombok.Getter;

@Getter
public class LockedException extends BaseAuthenticationException {

    private static final PredefinedCode CODE = PredefinedCode.USER_LOCKED;

    public LockedException() {
        super(CODE.getCode(), CODE.getMsg());
    }

    public LockedException(AuthError authError) {
        super(CODE.getCode(), CODE.getMsg(), authError);
    }

    public LockedException(AuthError authError, Throwable cause) {
        super(CODE.getCode(), CODE.getMsg(), authError, cause);
    }
}
