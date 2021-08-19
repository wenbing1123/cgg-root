package com.cgg.service.user.controller.v1;

import com.cgg.framework.captcha.KaptchaCreator;
import com.cgg.framework.config.properties.WechatProperties;
import com.cgg.framework.dto.response.None;
import com.cgg.framework.exception.BizFailException;
import com.cgg.framework.exception.SysException;
import com.cgg.framework.redis.RedisManager;
import com.cgg.framework.utils.IdUtils;
import com.cgg.service.account.api.SmsService;
import com.cgg.service.user.constants.UserConstants;
import com.cgg.service.user.controller.v1.parammodel.SendLoginVerifyCodePM;
import com.cgg.service.user.controller.v1.viewmodel.GetCaptchaCodeVM;
import com.cgg.service.user.controller.v1.viewmodel.GetWechatCodeVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("v1/login")
@Tag(name = "登录服务")
@Slf4j
public class LoginController {

    @Resource
    private SmsService smsService;

    @Resource
    private WechatProperties wechatProperties;

    @Resource
    private RedisManager redisManager;

    @GetMapping("sms_verify_code")
    @Operation(tags = "发送短信验证码")
    public Mono<None> sendLoginVerifyCode(SendLoginVerifyCodePM pm) {
        return smsService
                .sendVerifyCode(pm.getPhone(), UserConstants.TPL_CODE_LOGIN)
                .filter(BooleanUtils::isTrue)
                .switchIfEmpty(Mono.error(new BizFailException("验证码发送失败")))
                .thenReturn(None.INST);
    }

    @GetMapping("wechat_code")
    @Operation(tags = "获取微信授权码")
    public Mono<GetWechatCodeVM> getWechatCode() {
        // 第一步：用户同意授权，获取code
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wechatProperties.getLogin().getAppId() +
                "&redirect_uri=" + wechatProperties.getLogin().getCallbackUrl() +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        return Mono.just(GetWechatCodeVM
                .builder()
                .redirectUrl(url)
                .build());
    }

    @GetMapping("captcha_code")
    @Operation(tags = "获取图片验证码")
    public Mono<GetCaptchaCodeVM> getCaptchaCode(ServerHttpResponse response) {
        return Mono.defer(() -> {
            int width =200, height=69;
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            String randomText = KaptchaCreator.drawRandomText(width, height, verifyImg);
            String verifyCodeKey = IdUtils.randomUUID();
            return redisManager.setEX(UserConstants.REDIS_COLLECTION_LOGIN_CAPTCHA + verifyCodeKey, randomText, 60*5)
                    .map(b -> {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        try {
                            ImageIO.write(verifyImg, "png", baos);
                            return GetCaptchaCodeVM
                                    .builder()
                                    .sessionKey(verifyCodeKey)
                                    .image(Base64Utils.encodeToString(baos.toByteArray()))
                                    .build();
                        } catch (IOException e) {
                            String msg = "验证码生成失败";
                            log.error(msg, e);
                            throw new SysException(msg);
                        }
                    });
        }).subscribeOn(Schedulers.boundedElastic());
    }

}
