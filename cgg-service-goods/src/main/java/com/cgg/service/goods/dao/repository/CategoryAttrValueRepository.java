package com.cgg.service.goods.dao.repository;

import com.cgg.service.goods.dao.entity.CategoryAttrValue;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CategoryAttrValueRepository extends R2dbcRepository<CategoryAttrValue, Long> {
}
