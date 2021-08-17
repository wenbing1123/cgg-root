package com.cgg.service.order.dao.repository;

import com.cgg.service.order.dao.entity.RechargeOrder;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface RechargeOrderRepository extends R2dbcRepository<RechargeOrder, Long> {

    @Query("update t_recharge_order t set t.order_status = :newStatus where t.id = :orderId and t.order_status = :oldStatus")
    Mono<Integer> casUpdateStatus(Long orderId, Integer oldStatus, Integer newStatus);

}
