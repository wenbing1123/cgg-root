package com.cgg.service.pay.dao.repository;

import com.cgg.service.pay.dao.entity.Payment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PaymentRepository extends R2dbcRepository<Payment, Long> {
}
