package com.cgg.service.user.security.authentication;

import com.cgg.framework.dto.response.Response;
import com.cgg.framework.enums.PredefinedCode;
import com.cgg.framework.utils.JacksonUtils;
import com.cgg.service.user.api.UserService;
import com.cgg.service.user.dto.command.LoginLogSaveCommand;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.exception.base.BaseAuthenticationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

public class JsonServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    @Resource
    private UserService userService;

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        if (exception instanceof BaseAuthenticationException) {
            return Mono.just(exception)
                    .cast(BaseAuthenticationException.class)
                    .flatMap(e -> writeMessage(response, e.getCode(), e.getMsg(), e.getAuthError()));
        }

        return writeMessage(response, PredefinedCode.AUTHENTICATION_FAIL.getCode(), PredefinedCode.AUTHENTICATION_FAIL.getMsg(), new AuthError());
    }

    private Mono<Void> writeMessage(ServerHttpResponse response, String code, String msg, AuthError authError) {
        DataBuffer buffer = response.bufferFactory().wrap(JacksonUtils.writeValueAsString(Response.error(code, msg, authError.getData())).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(userService.saveLoginLog(LoginLogSaveCommand
                .builder()
                .resultCode(code)
                .resultMsg(msg)
                .loginWay(authError.getAuthType().getValue())
                .principal(authError.getPrincipal())
                .agent(StringUtils.EMPTY)
                .appId(StringUtils.EMPTY)
                .appVersion(StringUtils.EMPTY)
                .ip(StringUtils.EMPTY)
                .build()).thenReturn(buffer));
    }
}
