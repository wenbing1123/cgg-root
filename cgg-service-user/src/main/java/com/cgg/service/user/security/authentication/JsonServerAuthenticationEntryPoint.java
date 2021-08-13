package com.cgg.service.user.security.authentication;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.framework.dto.response.Response;
import com.cgg.framework.utils.JacksonUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class JsonServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    response.setStatusCode(HttpStatus.OK);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    DataBuffer buffer = dataBufferFactory.wrap(JacksonUtils.writeValueAsString(Response.error(PredefinedCode.NOT_LOGIN)).getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));
                });
    }
}
