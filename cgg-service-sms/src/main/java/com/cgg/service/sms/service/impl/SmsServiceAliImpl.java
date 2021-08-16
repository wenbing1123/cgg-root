package com.cgg.service.sms.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cgg.framework.enums.YesNo;
import com.cgg.framework.exception.*;
import com.cgg.framework.redis.RedisManager;
import com.cgg.framework.utils.DateUtils;
import com.cgg.framework.utils.JacksonUtils;
import com.cgg.service.baseconfig.service.ConfigService;
import com.cgg.service.sms.config.properties.SmsProperties;
import com.cgg.service.sms.constants.SmsConstants;
import com.cgg.service.sms.dao.entity.SmsLog;
import com.cgg.service.sms.dao.repository.SmsLogRepository;
import com.cgg.service.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

@Service("smsService")
@Slf4j
public class SmsServiceAliImpl implements SmsService, InitializingBean, DisposableBean {

    private IAcsClient client;

    @Resource
    private SmsProperties smsProperties;
    @Resource
    private SmsLogRepository smsLogRepository;
    @Resource
    private ConfigService configService;
    @Resource
    private RedisManager redisManager;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.client == null) {
            IClientProfile profile = DefaultProfile.getProfile(smsProperties.getRegionId(), smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
            this.client = new DefaultAcsClient(profile);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (this.client != null) {
            this.client.shutdown();
        }
    }

    @SafeVarargs
    @Override
    public final Mono<Boolean> sendMessage(String phone, String tplCode, Map<String, Object>... params) {
        String tplCountRedisKey = tplSmsSendCountKey(phone, tplCode);
        String allCountRedisKey = allSmsSendCountKey(phone);
        Mono<Integer> tplCount = redisManager.get(tplCountRedisKey)
                .cast(Integer.class)
                .defaultIfEmpty(0);
        Mono<Integer> allCount = redisManager.get(allCountRedisKey)
                .cast(Integer.class)
                .defaultIfEmpty(0);
        return Mono.defer(() -> tplCount
                    .zipWith(configService.getAsInteger(SmsConstants.CFG_NAME_SMS_SEND_TPL_DAILY_LIMIT, 5))
                    .filter(x -> x.getT1().compareTo(x.getT2()) < 0)
                    .switchIfEmpty(Mono.error(new UnconfirmedRuleException("短信发送次数超过日上限")))
                    .then(allCount)
                    .zipWith(configService.getAsInteger(SmsConstants.CFG_NAME_SMS_SEND_ALL_DAILY_LIMIT, 60))
                    .filter(x -> x.getT1().compareTo(x.getT2()) < 0)
                    .switchIfEmpty(Mono.error(new UnconfirmedRuleException("短信发送次数超过日上限")))
                    .then(Mono.defer(() -> {
                        SmsLog smsLog = SmsLog.builder()
                                .phone(phone)
                                .tplCode(tplCode)
                                .build();

                        SendSmsRequest request = new SendSmsRequest();
                        request.setPhoneNumbers(phone);
                        request.setSignName(smsProperties.getSignName());
                        request.setTemplateCode(tplCode);
                        if (params.length > 0 && MapUtils.isNotEmpty(params[0])) {
                            String paramJson = JacksonUtils.writeValueAsString(params[0]);
                            request.setTemplateParam(paramJson);
                            smsLog.setTplParams(paramJson);
                        }

                        try {
                            // SendSmsResponse sendSmsResponse = client.getAcsResponse(request);
                            SendSmsResponse sendSmsResponse = new SendSmsResponse();
                            sendSmsResponse.setCode("OK");
                            sendSmsResponse.setMessage("success");
                            if (log.isDebugEnabled()) {
                                log.debug("sendSmsRequest: {}, sendSmsResponse: {}", JacksonUtils.writeValueAsString(request), JacksonUtils.writeValueAsString(sendSmsResponse));
                            }

                            boolean result = false;
                            if(StringUtils.equals("OK", sendSmsResponse.getCode())) {
                                result = true;
                                smsLog.setResultCode("0");
                            } else {
                                smsLog.setResultCode("-1");
                                smsLog.setResultMsg(JacksonUtils.writeValueAsString(sendSmsResponse));
                            }
                            smsLogRepository.save(smsLog);
                            return Mono.just(result);
                        } catch (Exception e) {
                            String msg = "服务调用失败";
                            log.error(msg, e);
                            throw new SvcFailException(msg);
                        }
                    }))
                    .flatMap(b -> {
                        if (BooleanUtils.isTrue(b)) {
                            LocalDateTime expireTimeAt = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0, 0));
                            long expireTime = DateUtils.minus(expireTimeAt, LocalDateTime.now());
                            return redisManager.exists(tplCountRedisKey)
                                    .filter(BooleanUtils::isTrue)
                                    .switchIfEmpty(redisManager.setEX(tplCountRedisKey, 1, expireTime))
                                    .map(x -> redisManager.incr(tplCountRedisKey, 1))
                                    .then(redisManager.exists(allCountRedisKey))
                                    .filter(BooleanUtils::isTrue)
                                    .switchIfEmpty(redisManager.setEX(allCountRedisKey, 1, expireTime))
                                    .map(x -> redisManager.incr(allCountRedisKey, 1))
                                    .thenReturn(true);
                        }
                        return Mono.just(false);
                    })
        ).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public final Mono<Boolean> sendVerifyCode(String phone, String tplCode) {
        String code = RandomStringUtils.random(smsProperties.getCodeLength(), SmsConstants.VERIFY_CODE_STR);
        String redisKey = SmsConstants.REDIS_COLLECTION_SMS_VERIFY_CODE + phone;
        return redisManager.exists(redisKey)
                .filter(BooleanUtils::isNotTrue)
                .switchIfEmpty(Mono.just(true)) // 已发送
                .flatMap(x -> sendMessage(phone, tplCode, Collections.singletonMap("code", code)))
                .filter(BooleanUtils::isTrue)
                .switchIfEmpty(Mono.just(false)) // 发送失败
                .flatMap(x -> redisManager.setEX(redisKey, code, smsProperties.getCodeDuration()*60L));
    }

    @Override
    public Mono<Boolean> checkVerifyCode(String phone, String tplCode, String code) {
        String redisKey = SmsConstants.REDIS_COLLECTION_SMS_VERIFY_CODE + phone;
        return redisManager.get(redisKey)
                .switchIfEmpty(Mono.error(new DataNotFoundException("验证码未发送或已过期")))
                .cast(String.class)
                .filter(smsVerifyCode -> StringUtils.equals(smsVerifyCode, code))
                .switchIfEmpty(Mono.error(new BizFailException("验证码错误")))
                .flatMap(smsVerifyCode -> redisManager.del(redisKey).map(x -> x==1));
    }


    private String tplSmsSendCountKey(String phone, String tplCode) {
        return SmsConstants.REDIS_COLLECTION_TPL_SMS_SEND_COUNT + tplCode + ":" + phone;
    }

    private String allSmsSendCountKey(String phone) {
        return SmsConstants.REDIS_COLLECTION_TPL_SMS_SEND_COUNT + phone;
    }
}
