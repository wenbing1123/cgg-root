package com.cgg.service.goods.dao.repository;

import com.cgg.service.goods.dao.entity.Brand;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface BrandRepository extends R2dbcRepository<Brand, Long> {
}
