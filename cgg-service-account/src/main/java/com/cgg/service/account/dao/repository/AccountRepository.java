package com.cgg.service.account.dao.repository;

import com.cgg.service.account.dao.entity.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountRepository extends R2dbcRepository<Account, Long> {

    @Query("update t_account t set t.balance = t.balance + :money where t.id = :id")
    Mono<Integer> updateByAddMoney(long id, BigDecimal money);

    @Query("update t_account t set t.balance = t.balance - :money where t.id = :id and t.balance > :money")
    Mono<Integer> updateByCutMoney(long id, BigDecimal money);

    @Query("update t_account t set t.point = t.point + :point where t.id = :id")
    Mono<Integer> updateByAddPoint(long id, BigDecimal money);

    @Query("update t_account t set t.point = t.point - :point where t.id = :id and t.point > :point")
    Mono<Integer> updateByCutPoint(long id, BigDecimal point);

}
