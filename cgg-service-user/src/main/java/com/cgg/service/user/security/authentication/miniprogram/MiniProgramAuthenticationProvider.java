package com.cgg.service.user.security.authentication.miniprogram;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.cgg.framework.exception.SysException;
import com.cgg.service.user.api.UserService;
import com.cgg.service.user.dao.entity.UserWechat;
import com.cgg.service.user.dto.command.UserWechatSaveCommand;
import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.AuthenticationProvider;
import com.cgg.service.user.security.authentication.wechat.WechatAuthorizationToken;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Slf4j
public class MiniProgramAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private WxMaService wxService;
    @Resource
    private UserService userService;

    @Override
    public boolean isSupported(Authentication authentication) {
        return authentication instanceof MiniProgramAuthorizationToken;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .cast(MiniProgramAuthorizationToken.class)
                .map(token -> {
                    try {
                        WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(token.getCode());
                        String openid = session.getOpenid();
                        String sessionKey = session.getSessionKey();
                        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, token.getEncryptedData(), token.getIv());
                        return UserWechat
                                .builder()
                                .openId(openid)
                                .nickName(userInfo.getNickName())
                                .avatarUrl(userInfo.getAvatarUrl())
                                .gender(userInfo.getGender())
                                .province(userInfo.getProvince())
                                .city(userInfo.getCity())
                                .country(userInfo.getCountry())
                                .language(userInfo.getLanguage())
                                .unionId(userInfo.getUnionId())
                                .build();
                    } catch (WxErrorException e) {
                        String msg = "登录失败";
                        log.error(msg, e);
                        throw new SysException(msg);
                    }
                }).flatMap(uw -> {
                    // 微信帐号做来一个关联，来关联我们的账号体系
                    // 此处实现自己的保存用户信息逻辑
                    // TODO
                    return userService
                            .findByOpenId(uw.getOpenId())
                            .switchIfEmpty(userService.saveWechat(uw.covt(UserWechatSaveCommand.class)))
                            .map(x -> new WechatAuthorizationToken(SecurityUser
                                    .builder()
                                    .userId(Long.toString(x.getUserId()))
                                    .username(uw.getNickName())
                                    .build()));
                });
    }
}
