package com.cgg.service.pay.service.impl;

import com.cgg.framework.enums.YesNo;
import com.cgg.framework.exception.BizFailException;
import com.cgg.framework.exception.DataNotFoundException;
import com.cgg.framework.utils.JacksonUtils;
import com.cgg.service.pay.dao.entity.Payment;
import com.cgg.service.pay.dao.entity.PaymentLog;
import com.cgg.service.pay.dao.repository.PaymentLogRepository;
import com.cgg.service.pay.dao.repository.PaymentRepository;
import com.cgg.service.pay.enums.PayStatus;
import com.cgg.service.pay.service.dto.command.CallbackCmd;
import com.cgg.service.pay.service.dto.command.PayCmd;
import com.cgg.service.pay.service.dto.command.PrePayCmd;
import com.cgg.service.pay.service.dto.result.CallbackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service("payServiceTransactional")
@Slf4j
public class PayServiceTransactional {

    @Resource
    private PaymentRepository paymentRepository;
    @Resource
    private PaymentLogRepository paymentLogRepository;

    @Transactional
    public Mono<Payment> createOrderForPrePay(PrePayCmd cmd) {
        return paymentRepository
                .save(Payment
                        .builder()
                        .storeName(cmd.getStoreName())
                        .siteName(cmd.getSiteName())
                        .siteOrderSn(cmd.getSiteOrderSn())
                        .siteOrderDate(cmd.getSiteOrderDate())
                        .siteReturnUrl(cmd.getSiteReturnUrl())
                        .siteReturnAttach(cmd.getSiteReturnAttach())
                        .accountName(cmd.getAccountName())
                        .userIP(cmd.getUserIP())
                        .merID(cmd.getMerID())
                        .orderAmount(cmd.getOrderAmount())
                        .payGate(cmd.getPayGate())
                        .subGate(cmd.getSubGate())
                        .payOrderSn("") // 支付订单号
                        .orderStatus(PayStatus.UNPAID.getValue()) // 未支付
                        .notifyFlag(YesNo.NO.getValue())
                        .build())
                .flatMap(payment -> paymentLogRepository
                        .save(PaymentLog
                                .builder()
                                .orderId(payment.getId())
                                .orderStatus(payment.getOrderStatus())
                                .orderAttach(JacksonUtils.writeValueAsString(cmd))
                                .build())
                        .thenReturn(payment));
    }

    @Transactional
    public Mono<Payment> updateOrderForPay(PayCmd cmd) {
        return paymentRepository
                .findOne(Example.of(Payment
                        .builder()
                        .payOrderSn(cmd.getPayOrderSn())
                        .orderStatus(PayStatus.UNPAID.getValue()) // 未支付的订单才允许支付
                        .build()))
                .switchIfEmpty(Mono.error(new DataNotFoundException("订单不存在")))
                .flatMap(payment -> {
                    payment.setOrderStatus(PayStatus.PROCESSING.getValue());
                    payment.setPayGateDate(LocalDateTime.now());

                    return paymentRepository.save(payment);
                })
                .flatMap(payment -> paymentLogRepository
                        .save(PaymentLog
                                .builder()
                                .orderId(payment.getId())
                                .orderStatus(payment.getOrderStatus())
                                .orderAttach(JacksonUtils.writeValueAsString(cmd))
                                .build())
                        .thenReturn(payment));
    }

    @Transactional
    public Mono<Payment> updateOrderForCallback(CallbackResult cmd) {
        return paymentRepository
                .findOne(Example.of(Payment
                        .builder()
                        .payOrderSn(cmd.getPayOrderSn())
                        .build()))
                .switchIfEmpty(Mono.error(new DataNotFoundException("订单不存在")))
                .flatMap(payment -> {

                    payment.setOrderStatus(cmd.getOrderStatus());
                    payment.setBankStatus(cmd.getBankStatus());
                    if (cmd.isPaid()) { // 已支付
                        payment.setBankOrderSn(cmd.getBankOrderSn());
                        payment.setBankOrderDate(cmd.getBankOrderDate());
                    } else if (cmd.isFailed()) { // 已失败
                        payment.setFailReason(cmd.getFailReason());
                    } else {
                        // 其它状态不处理
                        String msg = "回调状态异常";
                        log.error(msg + ", payOrderSn: {}, status: {}, bankOrderStatus: {}", cmd.getPayOrderSn(), cmd.getOrderStatus(), cmd.getBankStatus());
                        return Mono.error(new BizFailException(msg));
                    }

                    return paymentRepository.save(payment);
                })
                .flatMap(payment -> paymentLogRepository
                        .save(PaymentLog
                                .builder()
                                .orderId(payment.getId())
                                .orderStatus(payment.getOrderStatus())
                                .orderAttach(JacksonUtils.writeValueAsString(cmd))
                                .build())
                        .thenReturn(payment));
    }

}
