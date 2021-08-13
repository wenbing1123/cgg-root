package com.cgg.framework.error;

import com.cgg.framework.dto.response.None;
import com.cgg.framework.dto.response.Response;
import com.cgg.framework.enums.PredefinedCode;
import com.cgg.framework.exception.BaseException;
import com.cgg.framework.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

import java.nio.charset.StandardCharsets;

@Component
@Order(Integer.MIN_VALUE)
@Slf4j
public class FilterExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);

        Response<None> resp;
        if (ex instanceof BaseException) {
            log.error("sys error: " + ex.getMessage(), ex);
            BaseException be = (BaseException)ex;
            resp = Response.error(be.getRetCode(), be.getRetInfo());
        } else {
            log.error("unknown error: " + ex.getMessage(), ex);
            resp = Response.error(PredefinedCode.ERROR);
        }

        DataBuffer buff = response.bufferFactory().allocateBuffer().write(JacksonUtils.writeValueAsString(resp).getBytes(StandardCharsets.UTF_8));
        response.getHeaders().setContentType(MediaType.APPLICATION_NDJSON);
        return response.writeAndFlushWith(Mono.just(ByteBufMono.just(buff)));
    }
}
