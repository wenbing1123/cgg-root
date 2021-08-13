package com.cgg.framework.advice;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.framework.exception.BizException;
import com.cgg.framework.dto.response.None;
import com.cgg.framework.dto.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;


@Order(1)
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 未知错误
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<Response<None>> error(Exception e) {
        log.error("unknown error: " + e.getMessage(), e);
        return  Mono.just(Response.error(PredefinedCode.ERROR));
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<Response<None>> error(BizException e) {
        log.error("sys error: " + e.getMessage(), e);
        return Mono.just(Response.error(e.getRetCode(), e.getRetInfo()));
    }

}
