package com.cgg.service.pay.gate.alipay;

import com.cgg.service.pay.dao.entity.Payment;
import com.cgg.service.pay.gate.GateService;
import com.cgg.service.pay.service.dto.command.CallbackCmd;
import com.cgg.service.pay.service.dto.command.PayCmd;
import com.cgg.service.pay.service.dto.command.PrePayCmd;
import com.cgg.service.pay.service.dto.result.CallbackResult;
import com.cgg.service.pay.service.dto.result.PayResult;
import com.cgg.service.pay.service.dto.result.PrePayResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("alipayGateService")
@Slf4j
public class GateServiceAlipayImpl implements GateService {

    @Override
    public Mono<PrePayResult> prePay(PrePayCmd cmd, Payment payment) {
        throw new UnsupportedOperationException("不支持的支付方式");
    }

    @Override
    public Mono<PayResult> pay(PayCmd cmd, Payment payment) {
        throw new UnsupportedOperationException("不支持API支付");
    }

    @Override
    public Mono<CallbackResult> callback(CallbackCmd cmd) {
        throw new UnsupportedOperationException("不支持的支付方式");
    }
    
}
