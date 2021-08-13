package com.cgg.service.user.exception.base;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public abstract class BaseAuthenticationException extends AuthenticationException {

    private final String code;
    private final String msg;
    private final AuthError authError;

    public BaseAuthenticationException(String code, String msg) {
        super(String.format("%s[code:%s]", msg, code));
        this.code = code;
        this.msg = msg;
        this.authError = null;
    }

    public BaseAuthenticationException(String code, String msg, AuthError authError) {
        super(String.format("%s[code:%s]", msg, code));
        this.code = code;
        this.msg = msg;
        this.authError = authError;
    }

    public BaseAuthenticationException(String code, String msg, AuthError authError, Throwable cause) {
        super(String.format("%s[code:%s]", msg, code), cause);
        this.code = code;
        this.msg = msg;
        this.authError = authError;
    }

}
