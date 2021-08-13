package com.cgg.service.order.dao.repository;

import com.cgg.service.order.dao.entity.RechargeOrder;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RechargeOrderRepository extends R2dbcRepository<RechargeOrder, Long> {
}
