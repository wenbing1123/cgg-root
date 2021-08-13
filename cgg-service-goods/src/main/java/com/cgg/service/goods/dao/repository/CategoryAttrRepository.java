package com.cgg.service.goods.dao.repository;

import com.cgg.service.goods.dao.entity.CategoryAttr;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CategoryAttrRepository extends R2dbcRepository<CategoryAttr, Long> {
}
