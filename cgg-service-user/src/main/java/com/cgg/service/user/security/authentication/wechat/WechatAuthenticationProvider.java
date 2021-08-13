package com.cgg.service.user.security.authentication.wechat;

import com.cgg.framework.config.properties.WechatProperties;
import com.cgg.service.user.dao.entity.UserWechat;
import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.AuthenticationProvider;
import com.cgg.service.user.service.UserService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Collections;

public class WechatAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private WechatProperties wechatProperties;
    @Resource
    private WebClient webClient;
    @Resource
    private UserService userService;


    @Override
    public boolean isSupported(Authentication authentication) {
        return authentication instanceof WechatAuthorizationToken;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        WechatAuthorizationToken token = (WechatAuthorizationToken) authentication;

        // 第二步：通过code换取网页授权access_token
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wechatProperties.getLogin().getAppId() +
                "&secret=" + wechatProperties.getLogin().getAppSecret() +
                "&code=" + token.getCode() +
                "&grant_type=authorization_code";

        return webClient
                .get()
                .uri(accessTokenUrl)
                .retrieve()
                .bodyToMono(WechatToken.class)
                .flatMap(t -> {
                    // 第三步：拉取用户信息(需scope为 snsapi_userinfo)
                    String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + t.getAccessToken() +
                            "&openid=" + t.getOpenId() +
                            "&lang=zh_CN";
                    return webClient
                            .get()
                            .uri(userInfoUrl)
                            .retrieve()
                            .bodyToMono(UserWechat.class);
                }).flatMap(uw -> {
                    // 微信帐号做来一个关联，来关联我们的账号体系
                    // 此处实现自己的保存用户信息逻辑
                    // TODO
                    return userService
                            .findByOpenId(uw.getOpenId())
                            .switchIfEmpty(userService.saveWechat(uw))
                            .map(x -> new WechatAuthorizationToken(SecurityUser
                                    .builder()
                                    .userId(Long.toString(x.getUserId()))
                                    .username(uw.getNickName())
                                    .build()));
                });
    }
}
