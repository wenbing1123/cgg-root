package com.cgg.service.pay.controller.v1;

import com.cgg.service.pay.api.PayService;
import com.cgg.service.pay.dto.command.CallbackCmd;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("v1/payment")
@Tag(name = "支付服务")
public class PayController {

    @Resource
    private PayService payService;

    @PostMapping("callback/{payGate}")
    Mono<ResponseEntity<?>> callback(@PathVariable("payGate") String payGate, ServerHttpRequest request) {
        return payService
                .callback(CallbackCmd
                    .builder()
                    .payGate(payGate)
                    .request(request)
                    .build())
                .map(result -> ResponseEntity
                        .ok()
                        .contentType(result.getReplyContentType())
                        .body(result.getReplyBody()));
    }

}
