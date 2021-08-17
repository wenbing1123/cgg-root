package com.cgg.service.order.service;

import com.cgg.service.order.service.dto.command.RechargePayNotifyCommand;
import com.cgg.service.order.service.dto.command.RechargePlaceOrderCommand;
import com.cgg.service.order.service.dto.command.RechargePrePayCommand;
import com.cgg.service.order.service.dto.response.RechargePlaceOrderResult;
import com.cgg.service.order.service.dto.response.RechargePrePayResult;
import reactor.core.publisher.Mono;

public interface RechargeService {

    Mono<RechargePlaceOrderResult> placeOrder(RechargePlaceOrderCommand cmd);
    Mono<RechargePrePayResult> prePay(RechargePrePayCommand cmd);
    Mono<Boolean> payNotify(RechargePayNotifyCommand cmd);

}
