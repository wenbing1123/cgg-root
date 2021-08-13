package com.cgg.service.user.security.authorization;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.framework.dto.response.Response;
import com.cgg.framework.utils.JacksonUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class JsonServerAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return Mono.defer(() ->
                Mono.just(exchange.getResponse())
                .flatMap(response -> {
                    response.setStatusCode(HttpStatus.OK);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    DataBuffer buffer = dataBufferFactory.wrap(JacksonUtils.writeValueAsString(Response.error(PredefinedCode.FORBIDDEN)).getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));
                }));
    }
}
