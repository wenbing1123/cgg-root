package com.cgg.service.user.security.authentication.jwt;

import com.cgg.framework.ensure.Ensure;
import com.cgg.framework.exception.ArgumentException;
import com.cgg.framework.exception.SysException;
import com.cgg.service.user.config.properties.JwtProperties;
import com.cgg.service.user.security.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.InitializingBean;

import java.util.Objects;

@Slf4j
public class JwtManagerJose4jImpl implements JwtManager, InitializingBean {

    private static final String CLAIM_USERNAME = "username";

    private final JwtProperties jwtProperties;
    private JsonWebSignature jws;
    private JwtConsumer consumer;

    public JwtManagerJose4jImpl(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jws = new JsonWebSignature();
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        jws.setKeyIdHeaderValue(jwtProperties.getKeyId());
        jws.setKey(new RsaJsonWebKey(JsonUtil.parseJson(jwtProperties.getPrivateKey())).getPrivateKey());

        consumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setMaxFutureValidityInMinutes(60*24*7) // 7天
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer(jwtProperties.getIssuer())
                .setExpectedAudience(StringUtils.EMPTY)
                .setVerificationKey(new RsaJsonWebKey(JsonUtil.parseJson(jwtProperties.getPublicKey())).getPublicKey())
                .build();
    }

    @Override
    public JwtToken createToken(SecurityUser user) {
        JwtClaims claims = new JwtClaims();
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setIssuer(jwtProperties.getIssuer());
        NumericDate date = NumericDate.now();
        date.addSeconds(jwtProperties.getExpiredSeconds());
        claims.setExpirationTime(date);
        claims.setNotBeforeMinutesInThePast(1);
        claims.setSubject(user.getUserId()); // 用户编号
        claims.setAudience(StringUtils.EMPTY);
        claims.setClaim(CLAIM_USERNAME, user.getUsername());

        jws.setPayload(claims.toJson());

        try {
            return JwtToken.of(jws.getCompactSerialization());
        } catch (JoseException e) {
            String msg = "Token创建失败";
            log.error(msg, e);
            throw new SysException(msg);
        }
    }

    @Override
    public SecurityUser verifyToken(String token) {
        try {
            JwtClaims claims = consumer.processToClaims(token);
            Ensure.that(Objects.nonNull(claims), new ArgumentException("Token无效"));

            return SecurityUser
                    .builder()
                    .userId(claims.getSubject())
                    .username(claims.getClaimValueAsString(CLAIM_USERNAME))
                    .build();
        } catch (InvalidJwtException | MalformedClaimException e) {
            String msg = "Token无效";
            log.error(msg, e);
            throw new ArgumentException(msg);
        }
    }
}
