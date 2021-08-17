package com.cgg.service.order.controller.v1;

import com.cgg.service.order.service.RechargeService;
import com.cgg.service.order.service.dto.command.RechargePlaceOrderCommand;
import com.cgg.service.order.service.dto.command.RechargePrePayCommand;
import com.cgg.service.order.service.dto.response.RechargePlaceOrderResult;
import com.cgg.service.order.service.dto.response.RechargePrePayResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("v1/recharges")
@Tag(name = "充值服务")
public class RechargeController {

    @Resource
    private RechargeService rechargeService;

    @PostMapping("place_order")
    @Operation(description = "下单")
    public Mono<RechargePlaceOrderResult> placeOrder(RechargePlaceOrderCommand cmd) {
        return rechargeService.placeOrder(cmd);
    }

    @PostMapping("pre_pay")
    @Operation(description = "订单预支付")
    public Mono<RechargePrePayResult> prePay(RechargePrePayCommand cmd) {
        return rechargeService.prePay(cmd);
    }

}
