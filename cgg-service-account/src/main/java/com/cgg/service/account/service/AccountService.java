package com.cgg.service.account.service;

import com.cgg.service.account.dao.entity.Account;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountService {

    Mono<Account> createAccount(long userId); // 创建帐户
    Mono<Boolean> addBalance(long accountId, BigDecimal money);
    Mono<Boolean> cutBalance(long accountId, BigDecimal money);
    Mono<Boolean> addPoint(long accountId, BigDecimal point);
    Mono<Boolean> cutPoint(long accountId, BigDecimal point);
}
