package com.cgg.service.order.dao.repository;

import com.cgg.service.order.dao.entity.GoodsOrder;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface GoodsOrderRepository extends R2dbcRepository<GoodsOrder, Long> {
}

