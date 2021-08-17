package com.cgg.service.pay.dao.repository;

import com.cgg.service.pay.dao.entity.Payment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PaymentRepository extends R2dbcRepository<Payment, Long> {

    @Query("update t_payment t set t.notify_flag = 1 where t.id = :id")
    Mono<Integer> updateNotify(long id);

}
