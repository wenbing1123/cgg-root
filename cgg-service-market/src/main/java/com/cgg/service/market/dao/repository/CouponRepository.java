package com.cgg.service.shopcart.dao.repository;

import com.cgg.service.shopcart.dao.entity.Coupon;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CouponRepository extends R2dbcRepository<Coupon, Long> {
}
