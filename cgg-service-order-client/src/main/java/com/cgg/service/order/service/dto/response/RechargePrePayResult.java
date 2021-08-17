package com.cgg.service.order.service.dto.response;

import com.cgg.framework.dto.response.ResponseData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RechargePrePayResult extends ResponseData {

    private String payOrderSn; //支付订单号
    private BigDecimal orderAmount; //支付金额

}
