package com.cgg.service.order.service.dto.command;

import com.cgg.framework.dto.request.Command;
import com.cgg.framework.ensure.Ensure;
import com.cgg.framework.validate.Validate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RechargePrePayCommand extends Command implements Validate {

    private String orderNo; //充值单号
    private String payGate; //支付网关

    @Override
    public void validate() {
        Ensure.paramNotBlank(orderNo, "订单号为空");
        Ensure.paramNotBlank(payGate, "支付网关为空");
    }
}
