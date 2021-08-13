package com.cgg.service.order.dao.repository;

import com.cgg.service.order.dao.entity.GoodsOrderItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface GoodsOrderItemRepository extends R2dbcRepository<GoodsOrderItem, Long> {
}

