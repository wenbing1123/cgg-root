package com.cgg.service.shopcart.dao.repository;

import com.cgg.service.shopcart.dao.entity.Cart;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CartRepository extends R2dbcRepository<Cart, Long> {
}
