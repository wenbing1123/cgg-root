package com.cgg.service.order.service;

import com.cgg.service.order.api.PurchaseService;
import com.cgg.service.order.dto.command.PayNotifyCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("purchaseService")
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {


    @Override
    public Mono<Boolean> payNotify(PayNotifyCommand cmd) {
        return null;
    }

}
