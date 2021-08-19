package com.cgg.service.pay.service;

import com.cgg.service.pay.api.PayService;
import com.cgg.service.pay.dto.command.CallbackCmd;
import com.cgg.service.pay.dto.command.PayCmd;
import com.cgg.service.pay.dto.command.PrePayCmd;
import com.cgg.service.pay.dto.response.CallbackResult;
import com.cgg.service.pay.dto.response.PayResult;
import com.cgg.service.pay.dto.response.PrePayResult;
import com.cgg.service.pay.gate.GateService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;

@Service("payService")
@Slf4j
public class PayServiceImpl implements PayService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private PayServiceTran payServiceTran;

    @Override
    public Mono<PrePayResult> prePay(PrePayCmd cmd) {
        GateService gateService = getGateService(cmd.getPayGate());
        return payServiceTran
                .createOrderForPrePay(cmd)
                .flatMap(payment -> gateService.prePay(cmd, payment));
    }

    @Override
    public Mono<PayResult> pay(PayCmd cmd) {
        GateService gateService = getGateService(cmd.getPayGate());
        return payServiceTran
                .updateOrderForPay(cmd)
                .flatMap(payment -> gateService.pay(cmd, payment));
    }

    @Override
    public Mono<CallbackResult> callback(CallbackCmd cmd) {
        GateService gateService = getGateService(cmd.getPayGate());
        return gateService.callback(cmd)
                .flatMap(result -> payServiceTran
                        .updateOrderForCallback(result)
                        .doOnNext(payment -> payServiceTran
                                .updateOrderForNotify(payment)
                                .subscribeOn(Schedulers.boundedElastic())
                                .subscribe())
                        .thenReturn(result));
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private GateService getGateService(String payGate) {
        return this.applicationContext.getBean(payGate + "GateService", GateService.class);
    }

}
