package com.cgg.service.account.dao.repository;

import com.cgg.service.account.dao.entity.AccountLog;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface AccountLogRepository extends R2dbcRepository<AccountLog, Long> {
}
