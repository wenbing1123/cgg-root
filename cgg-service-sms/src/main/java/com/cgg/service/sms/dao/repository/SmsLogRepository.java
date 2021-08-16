package com.cgg.service.sms.dao.repository;

import com.cgg.service.sms.dao.entity.SmsLog;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface SmsLogRepository extends R2dbcRepository<SmsLog, Long> {
}
