package com.cgg.service.pay.dao.repository;

import com.cgg.service.pay.dao.entity.PaymentLog;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PaymentLogRepository extends R2dbcRepository<PaymentLog, Long> {
}
