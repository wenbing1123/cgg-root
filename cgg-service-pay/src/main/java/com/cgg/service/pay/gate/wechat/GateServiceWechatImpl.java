package com.cgg.service.pay.gate.wechat;

import com.cgg.framework.config.properties.WechatProperties;
import com.cgg.framework.exception.SvcFailException;
import com.cgg.framework.utils.DateUtils;
import com.cgg.framework.utils.JacksonUtils;
import com.cgg.service.pay.dao.entity.Payment;
import com.cgg.service.pay.dto.command.CallbackCmd;
import com.cgg.service.pay.dto.command.PayCmd;
import com.cgg.service.pay.dto.command.PrePayCmd;
import com.cgg.service.pay.dto.response.CallbackResult;
import com.cgg.service.pay.dto.response.PayResult;
import com.cgg.service.pay.dto.response.PrePayResult;
import com.cgg.service.pay.enums.PayStatus;
import com.cgg.service.pay.gate.GateService;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service("wechatGateService")
@Slf4j
public class GateServiceWechatImpl implements GateService {

    @Resource
    private WechatProperties wechatProperties;
    @Resource
    private WxPayService wxPayService;

    @Override
    public Mono<PrePayResult> prePay(PrePayCmd cmd, Payment payment) {
        return Mono.fromSupplier(() -> {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setBody(cmd.getGoodsName());
            orderRequest.setOutTradeNo(payment.getPayOrderSn()); //???????????????
            orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(payment.getOrderAmount().toPlainString())); // ????????????
            orderRequest.setSpbillCreateIp(WechatConstants.CREATE_IP);
            orderRequest.setNotifyUrl(wechatProperties.getPay().getNotifyUrl());
            orderRequest.setTradeType(WechatConstants.TRADE_TYPE);
            try {
                WxPayMpOrderResult orderResult = wxPayService.createOrder(orderRequest);
                return PrePayResult
                        .builder()
                        .payGate(WechatConstants.PAY_GATE)
                        .payOrderSn(payment.getPayOrderSn())
                        .extend(WechatConstants.ATTACH_KEY, JacksonUtils.writeValueAsString(orderResult))
                        .build();
            } catch (WxPayException e) {
                String msg = "??????????????????";
                log.error(msg, e);
                throw new SvcFailException(msg);
            }
        });
    }

    @Override
    public Mono<PayResult> pay(PayCmd cmd, Payment payment) {
        throw new UnsupportedOperationException("?????????API??????");
    }

    @Override
    public Mono<CallbackResult> callback(CallbackCmd cmd) {
        return DataBufferUtils.join(cmd.getRequest().getBody()).map(buffer -> {
            String xmlData = buffer.toString(StandardCharsets.UTF_8);

            try {
                WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
                return CallbackResult
                        .builder()
                        .payGate(WechatConstants.PAY_GATE)
                        .payOrderSn(notifyResult.getOutTradeNo()) //?????????
                        .orderAmount(new BigDecimal(BaseWxPayResult.fenToYuan(notifyResult.getTotalFee()))) //??????
                        .bankOrderSn(notifyResult.getTransactionId()) //???????????????
                        .bankStatus(notifyResult.getResultCode())
                        .bankOrderDate(DateUtils.parseLocalDateTime(DateUtils.NON_SEP_DATETIME))
                        .bankAttach(StringUtils.EMPTY)
                        .failReason(notifyResult.getReturnMsg())
                        .orderStatus(WechatConstants.isPaid(notifyResult.getReturnCode(),notifyResult.getReturnCode()) ? PayStatus.PAID.getValue() : PayStatus.UNPAID.getValue())
                        .replyContentType(MediaType.APPLICATION_XML)
                        .replyBody(WxPayNotifyResponse.success("ok"))
                        .build();
            } catch (WxPayException e) {
                log.error("??????????????????", e);
                return CallbackResult
                        .builder()
                        .replyContentType(MediaType.APPLICATION_XML)
                        .replyBody(WxPayNotifyResponse.fail(e.getMessage()))
                        .build();
            }
        });
    }

}
