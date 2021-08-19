package com.cgg.service.pay.api;

import com.cgg.service.pay.dto.command.CallbackCmd;
import com.cgg.service.pay.dto.command.PayCmd;
import com.cgg.service.pay.dto.command.PrePayCmd;
import com.cgg.service.pay.dto.response.CallbackResult;
import com.cgg.service.pay.dto.response.PayResult;
import com.cgg.service.pay.dto.response.PrePayResult;
import reactor.core.publisher.Mono;

public interface PayService {

    /**
     * 根据产品订单创建支付订单
     */
    Mono<PrePayResult> prePay(PrePayCmd cmd);

    /**
     * 根据支付订单号进行支付
     */
    Mono<PayResult> pay(PayCmd cmd);

    /**
     * 根据支付订单号回调通知
     */
    Mono<CallbackResult> callback(CallbackCmd cmd);

}
