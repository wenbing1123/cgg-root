package com.cgg.service.user.exception;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.exception.base.BaseAuthenticationException;
import lombok.Getter;

@Getter
public class DisabledException extends BaseAuthenticationException {

    private static final PredefinedCode CODE = PredefinedCode.USER_DISABLED;

    public DisabledException() {
        super(CODE.getCode(), CODE.getMsg());
    }

    public DisabledException(AuthError authError) {
        super(CODE.getCode(), CODE.getMsg(), authError);
    }

    public DisabledException(AuthError authError, Throwable cause) {
        super(CODE.getCode(), CODE.getMsg(), authError, cause);
    }
}
