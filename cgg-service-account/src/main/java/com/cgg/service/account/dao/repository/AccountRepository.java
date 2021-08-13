package com.cgg.service.account.dao.repository;

import com.cgg.service.account.dao.entity.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface AccountRepository extends R2dbcRepository<Account, Long> {
}
