package com.cgg.service.pay.gate.alipay;

import com.cgg.service.pay.dao.entity.Payment;
import com.cgg.service.pay.dto.command.CallbackCmd;
import com.cgg.service.pay.dto.command.PayCmd;
import com.cgg.service.pay.dto.command.PrePayCmd;
import com.cgg.service.pay.dto.response.CallbackResult;
import com.cgg.service.pay.dto.response.PayResult;
import com.cgg.service.pay.dto.response.PrePayResult;
import com.cgg.service.pay.gate.GateService;
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
