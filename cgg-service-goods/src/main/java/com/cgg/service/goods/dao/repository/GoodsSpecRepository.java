package com.cgg.service.goods.dao.repository;

import com.cgg.service.goods.dao.entity.Goods;
import com.cgg.service.goods.dao.entity.GoodsSpec;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface GoodsSpecRepository extends R2dbcRepository<GoodsSpec, Long> {
}
