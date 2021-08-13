package com.cgg.framework.aspect;

import com.cgg.framework.exception.SysException;
import com.cgg.framework.dto.response.None;
import com.cgg.framework.dto.response.Response;
import com.cgg.framework.dto.response.ResponseData;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Aspect
@Component
@ConditionalOnClass(name = {"org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler"})
public class ResponseBodyResultHandlerAspect {

    @SneakyThrows
    @Around(value = "execution(* org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler.handleResult(..)) && args(exchange, result)", argNames = "point,exchange,result")
    public Object handleResult(ProceedingJoinPoint point, ServerWebExchange exchange, HandlerResult result) {
        if (!result.getHandler().toString().startsWith("com.cgg")) {
            return point.proceed();
        }

        Object res = result.getReturnValue();

        if (res instanceof Mono) {
            Mono responseMono = ((Mono) result.getReturnValue())
                    .defaultIfEmpty(Response.success())
                    .map(responseValue -> {
                        if (responseValue instanceof Response) return responseValue;
                        if (responseValue instanceof ResponseData) return responseValue instanceof None ? Response.success() : Response.success((ResponseData)responseValue);
                        throw new SysException("result type must be Result or RetBody!");
                    });
            return point.proceed(Arrays.asList(
                    exchange,
                    new HandlerResult(result.getHandler(), responseMono, result.getReturnTypeSource())
            ).toArray());
        }

        if (res instanceof Flux) {
            Mono responseMono = ((Flux) result.getReturnValue()).collectList()
                    .defaultIfEmpty(Response.success())
                    .map(responseValue -> {
                        if (responseValue instanceof Response) return responseValue;
                        if (responseValue instanceof ResponseData) return responseValue instanceof None ? Response.success() : Response.success((ResponseData)responseValue);
                        throw new SysException("result type must be Result or RetBody!");
                    });
            return point.proceed(Arrays.asList(
                    exchange,
                    new HandlerResult(result.getHandler(), responseMono, result.getReturnTypeSource())
            ).toArray());
        }

        throw new SysException("result type must be mono or flux!");
    }

}
