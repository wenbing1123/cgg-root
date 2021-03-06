package com.cgg.service.order.service;

import com.cgg.framework.exception.BizFailException;
import com.cgg.framework.exception.DataNotFoundException;
import com.cgg.framework.utils.IdUtils;
import com.cgg.service.account.api.AccountService;
import com.cgg.service.order.api.RechargeService;
import com.cgg.service.order.constants.OrderConstants;
import com.cgg.service.order.dao.entity.RechargeOrder;
import com.cgg.service.order.dao.repository.RechargeOrderRepository;
import com.cgg.service.order.dto.command.PayNotifyCommand;
import com.cgg.service.order.dto.command.RechargePlaceOrderCommand;
import com.cgg.service.order.dto.command.RechargePrePayCommand;
import com.cgg.service.order.dto.response.RechargePlaceOrderResult;
import com.cgg.service.order.dto.response.RechargePrePayResult;
import com.cgg.service.order.enums.RechargeStatus;
import com.cgg.service.pay.api.PayService;
import com.cgg.service.pay.dto.command.PrePayCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Service("rechargeService")
@Slf4j
public class RechargeServiceImpl implements RechargeService {

    @Resource
    private RechargeOrderRepository rechargeOrderRepository;
    @Resource
    private PayService payService;

    @Override
    public Mono<RechargePlaceOrderResult> placeOrder(RechargePlaceOrderCommand cmd) {
        return rechargeOrderRepository
                .save(RechargeOrder
                        .builder()
                        .orderAmount(cmd.getOrderAmount())
                        .orderNo(IdUtils.bizNo(OrderConstants.ORDER_NO_PREFIX_RECHARGE))
                        .build())
                .map(order -> RechargePlaceOrderResult
                        .builder()
                        .orderNo(order.getOrderNo())
                        .build());
    }

    @Override
    @Transactional
    public Mono<RechargePrePayResult> prePay(RechargePrePayCommand cmd) {
        return rechargeOrderRepository
                .findOne(Example.of(RechargeOrder
                        .builder()
                        .orderNo(cmd.getOrderNo())
                        .orderStatus(RechargeStatus.UNPAID.getValue()) // ??????????????????
                        .build()))
                .switchIfEmpty(Mono.error(new DataNotFoundException("???????????????")))
                .flatMap(order -> rechargeOrderRepository
                        .casUpdateStatus(order.getId(), RechargeStatus.UNPAID.getValue(), RechargeStatus.PAYING.getValue())
                        .filter(c -> c==1)
                        .switchIfEmpty(Mono.error(new BizFailException("????????????")))
                        .then(payService.prePay(PrePayCmd
                                .builder()
                                .payGate(cmd.getPayGate())
                                .storeName("wallet")
                                .siteName("wallet")
                                .siteOrderSn(order.getOrderNo())
                                .siteOrderDate(order.getGmtCreate())
                                .orderAmount(order.getOrderAmount())
                                .goodsName("??????")
                                .build())))
                .map(result -> RechargePrePayResult
                        .builder()
                        .payOrderSn(result.getPayOrderSn())
                        .build());
    }

    @Override
    @Transactional
    public Mono<Boolean> payNotify(PayNotifyCommand cmd) {
        return rechargeOrderRepository
                .findOne(Example.of(RechargeOrder
                        .builder()
                        .orderNo(cmd.getOrderNo())
                        .build()))
                .switchIfEmpty(Mono.error(new DataNotFoundException("???????????????")))
                .flatMap(order -> rechargeOrderRepository
                        .casUpdateStatus(order.getId(),RechargeStatus.PAYING.getValue(), RechargeStatus.PAID.getValue())
                        .filter(c -> c==1)
                        .switchIfEmpty(Mono.error(new BizFailException("????????????")))
                        .then(accountService.addBalance(order.getUserId(), order.getOrderAmount())));
    }

    @Resource
    private AccountService accountService;
}
