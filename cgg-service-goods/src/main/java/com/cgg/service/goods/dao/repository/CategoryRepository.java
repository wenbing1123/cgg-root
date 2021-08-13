package com.cgg.service.goods.dao.repository;

import com.cgg.service.goods.dao.entity.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CategoryRepository extends R2dbcRepository<Category, Long> {
}
