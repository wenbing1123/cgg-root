package com.cgg.service.pay.gate;

import com.cgg.service.pay.dao.entity.Payment;
import com.cgg.service.pay.service.dto.command.CallbackCmd;
import com.cgg.service.pay.service.dto.command.PayCmd;
import com.cgg.service.pay.service.dto.command.PrePayCmd;
import com.cgg.service.pay.service.dto.result.CallbackResult;
import com.cgg.service.pay.service.dto.result.PayResult;
import com.cgg.service.pay.service.dto.result.PrePayResult;
import reactor.core.publisher.Mono;

public interface GateService {

    Mono<PrePayResult> prePay(PrePayCmd cmd, Payment payment);
    Mono<PayResult> pay(PayCmd cmd, Payment payment);
    Mono<CallbackResult> callback(CallbackCmd cmd);

}
