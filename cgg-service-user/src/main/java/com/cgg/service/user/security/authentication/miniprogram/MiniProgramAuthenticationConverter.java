package com.cgg.service.user.security.authentication.miniprogram;

import com.cgg.framework.exception.ArgumentException;
import com.cgg.framework.utils.JacksonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MiniProgramAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        return DataBufferUtils.join(request.getBody())
                .map(dataBuffer -> {
                    String respBody = dataBuffer.toString(StandardCharsets.UTF_8);
                    Map<String, String> map = JacksonUtils.readValueToBean(respBody, Map.class);
                    String code = map.get("code");
                    String encryptedData = map.get("encrypted_data");
                    String iv = map.get("iv");

                    if (StringUtils.isAnyBlank(code, encryptedData, iv)) {
                        throw new ArgumentException("参数错误");
                    }

                    return new MiniProgramAuthorizationToken(code, encryptedData, iv);
                });
    }

}
