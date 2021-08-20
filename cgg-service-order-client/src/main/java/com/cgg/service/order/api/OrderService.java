package com.cgg.service.order.api;

import com.cgg.service.order.dto.command.PayNotifyCommand;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<Boolean> payNotify(PayNotifyCommand cmd);

}
