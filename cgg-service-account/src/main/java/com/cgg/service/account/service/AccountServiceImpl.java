package com.cgg.service.account.service;

import com.cgg.framework.exception.BizFailException;
import com.cgg.framework.exception.DataNotFoundException;
import com.cgg.service.account.api.AccountService;
import com.cgg.service.account.dao.entity.Account;
import com.cgg.service.account.dao.entity.AccountLog;
import com.cgg.service.account.dao.repository.AccountLogRepository;
import com.cgg.service.account.dao.repository.AccountRepository;
import com.cgg.service.account.dto.response.AccountResult;
import com.cgg.service.account.enums.OperateType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("accountService")
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountRepository accountRepository;
    @Resource
    private AccountLogRepository accountLogRepository;


    @Override
    @Transactional
    public Mono<AccountResult> createAccount(long userId) {
        return accountRepository
                .save(Account
                        .builder()
                        .id(userId)
                        .version(1L)
                        .balance(BigDecimal.ZERO)
                        .point(BigDecimal.ZERO)
                        .build())
                .map(account -> AccountResult
                        .builder()
                        .accountId(account.getId())
                        .build());
    }

    @Override
    @Transactional
    public Mono<Boolean> addBalance(long accountId, BigDecimal money) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new DataNotFoundException("帐户不存在")))
                .flatMap(account -> accountRepository
                        .updateByAddMoney(account.getId(), money)
                        .filter(c -> c==1)
                        .switchIfEmpty(Mono.error(new BizFailException("余额增加失败")))
                        .map(c -> accountLogRepository.save(AccountLog
                                .builder()
                                .accountId(accountId)
                                .operateType(OperateType.BALANCE_ADD.getValue())
                                .value(money)
                                .refId(StringUtils.EMPTY)
                                .remark(StringUtils.EMPTY)
                                .build()))
                        .thenReturn(true));
    }

    @Override
    @Transactional
    public Mono<Boolean> cutBalance(long accountId, BigDecimal money) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new DataNotFoundException("帐户不存在")))
                .flatMap(account -> accountRepository
                        .updateByCutMoney(account.getId(), money)
                        .filter(c -> c==1)
                        .switchIfEmpty(Mono.error(new BizFailException("余额扣减失败")))
                        .map(c -> accountLogRepository.save(AccountLog
                                .builder()
                                .accountId(accountId)
                                .operateType(OperateType.BALANCE_CUT.getValue())
                                .value(money)
                                .refId(StringUtils.EMPTY)
                                .remark(StringUtils.EMPTY)
                                .build()))
                        .thenReturn(true));
    }

    @Override
    @Transactional
    public Mono<Boolean> addPoint(long accountId, BigDecimal point) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new DataNotFoundException("帐户不存在")))
                .flatMap(account -> accountRepository
                        .updateByAddPoint(account.getId(), point)
                        .filter(c -> c==1)
                        .switchIfEmpty(Mono.error(new BizFailException("积分增加失败")))
                        .map(c -> accountLogRepository.save(AccountLog
                                .builder()
                                .accountId(accountId)
                                .operateType(OperateType.POINT_ADD.getValue())
                                .value(point)
                                .refId(StringUtils.EMPTY)
                                .remark(StringUtils.EMPTY)
                                .build()))
                        .thenReturn(true));
    }

    @Override
    @Transactional
    public Mono<Boolean> cutPoint(long accountId, BigDecimal point) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new DataNotFoundException("帐户不存在")))
                .flatMap(account -> accountRepository
                        .updateByCutPoint(account.getId(), point)
                        .filter(c -> c==1)
                        .switchIfEmpty(Mono.error(new BizFailException("积分扣减失败")))
                        .map(c -> accountLogRepository.save(AccountLog
                                .builder()
                                .accountId(accountId)
                                .operateType(OperateType.POINT_CUT.getValue())
                                .value(point)
                                .refId(StringUtils.EMPTY)
                                .remark(StringUtils.EMPTY)
                                .build()))
                        .thenReturn(true));
    }
}
