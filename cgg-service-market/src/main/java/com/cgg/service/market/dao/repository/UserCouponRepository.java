package com.cgg.service.market.dao.repository;

import com.cgg.service.market.dao.entity.UserCoupon;
import com.cgg.service.shopcart.dao.entity.Coupon;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserCouponRepository extends R2dbcRepository<UserCoupon, Long> {
}
