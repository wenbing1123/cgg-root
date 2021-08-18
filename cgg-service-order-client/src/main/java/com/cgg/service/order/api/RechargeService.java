package com.cgg.service.order.api;

import com.cgg.service.order.dto.command.RechargePayNotifyCommand;
import com.cgg.service.order.dto.command.RechargePlaceOrderCommand;
import com.cgg.service.order.dto.command.RechargePrePayCommand;
import com.cgg.service.order.dto.response.RechargePlaceOrderResult;
import com.cgg.service.order.dto.response.RechargePrePayResult;
import reactor.core.publisher.Mono;

public interface RechargeService {

    Mono<RechargePlaceOrderResult> placeOrder(RechargePlaceOrderCommand cmd);
    Mono<RechargePrePayResult> prePay(RechargePrePayCommand cmd);
    Mono<Boolean> payNotify(RechargePayNotifyCommand cmd);

}
