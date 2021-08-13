package com.cgg.service.goods.dao.repository;

import com.cgg.service.goods.dao.entity.Goods;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface GoodsRepository extends R2dbcRepository<Goods, Long> {
}
