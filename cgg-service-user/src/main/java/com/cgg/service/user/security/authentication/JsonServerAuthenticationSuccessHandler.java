package com.cgg.service.user.security.authentication;

import com.cgg.framework.dto.response.Response;
import com.cgg.framework.ensure.Ensure;
import com.cgg.framework.exception.SysException;
import com.cgg.framework.utils.JacksonUtils;
import com.cgg.service.user.api.UserService;
import com.cgg.service.user.dto.command.LoginLogSaveCommand;
import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.jwt.JwtManager;
import com.cgg.service.user.security.authentication.phone.PhoneAuthorizationToken;
import com.cgg.service.user.security.authentication.wechat.WechatAuthorizationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

public class JsonServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Resource
    private JwtManager jwtManager;
    @Resource
    private UserService userService;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Ensure.that(authentication.isAuthenticated(), new SysException("未认证"));
        SecurityUser user = ((BaseAuthenticationToken)authentication).getUser();
        DataBuffer buffer = response.bufferFactory().wrap(JacksonUtils.writeValueAsString(Response.success(jwtManager.createToken(user))).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(userService.saveLoginLog(LoginLogSaveCommand
                .builder()
                .principal(user.getUserId())
                .resultCode("0")
                .resultMsg(StringUtils.EMPTY)
                .loginWay(loginWay(authentication))
                .agent(StringUtils.EMPTY)
                .appId(StringUtils.EMPTY)
                .appVersion(StringUtils.EMPTY)
                .ip(StringUtils.EMPTY)
                .build()).thenReturn(buffer));
    }

    private int loginWay(Authentication authentication) {
        if (authentication instanceof PhoneAuthorizationToken) return 1;
        if (authentication instanceof WechatAuthorizationToken) return 2;
        return 0;
    }
}
