package com.cgg.service.pay.service;

import com.cgg.framework.enums.YesNo;
import com.cgg.framework.exception.BizFailException;
import com.cgg.framework.exception.DataNotFoundException;
import com.cgg.framework.utils.IdUtils;
import com.cgg.framework.utils.JacksonUtils;
import com.cgg.service.order.api.OrderService;
import com.cgg.service.order.api.RechargeService;
import com.cgg.service.order.dto.command.PayNotifyCommand;
import com.cgg.service.order.factory.OrderServiceFactory;
import com.cgg.service.pay.constants.PayConstants;
import com.cgg.service.pay.dao.entity.Payment;
import com.cgg.service.pay.dao.entity.PaymentLog;
import com.cgg.service.pay.dao.repository.PaymentLogRepository;
import com.cgg.service.pay.dao.repository.PaymentRepository;
import com.cgg.service.pay.dto.command.PayCmd;
import com.cgg.service.pay.dto.command.PrePayCmd;
import com.cgg.service.pay.dto.response.CallbackResult;
import com.cgg.service.pay.enums.PayStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service("payServiceTran")
@Slf4j
public class PayServiceTran {

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
                        .payOrderSn(IdUtils.bizNo(PayConstants.ORDER_NO_PREFIX)) // ???????????????
                        .orderStatus(PayStatus.UNPAID.getValue()) // ?????????
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
                        .orderStatus(PayStatus.UNPAID.getValue()) // ?????????????????????????????????
                        .build()))
                .switchIfEmpty(Mono.error(new DataNotFoundException("???????????????")))
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
                .switchIfEmpty(Mono.error(new DataNotFoundException("???????????????")))
                .flatMap(payment -> {

                    payment.setOrderStatus(cmd.getOrderStatus());
                    payment.setBankStatus(cmd.getBankStatus());
                    if (cmd.isPaid()) { // ?????????
                        payment.setBankOrderSn(cmd.getBankOrderSn());
                        payment.setBankOrderDate(cmd.getBankOrderDate());
                    } else if (cmd.isFailed()) { // ?????????
                        payment.setFailReason(cmd.getFailReason());
                    } else {
                        // ?????????????????????
                        String msg = "??????????????????";
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

    @Transactional
    public Mono<Boolean> updateOrderForNotify(Payment payment) {
        OrderService orderService = orderServiceFactory.getOrderService(payment.getSiteName());
        return orderService
                .payNotify(PayNotifyCommand
                        .builder()
                        .orderNo(payment.getSiteOrderSn())
                        .build())
                .filter(BooleanUtils::isTrue)
                .doOnNext(b -> paymentRepository.updateNotify(payment.getId()));
    }

    @Resource
    private OrderServiceFactory orderServiceFactory;

}
