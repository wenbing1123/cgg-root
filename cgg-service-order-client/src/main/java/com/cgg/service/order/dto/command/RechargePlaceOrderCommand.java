package com.cgg.service.order.dto.command;

import com.cgg.framework.dto.request.Command;
import com.cgg.framework.ensure.Ensure;
import com.cgg.framework.validate.Validate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RechargePlaceOrderCommand extends Command implements Validate {

    private BigDecimal orderAmount;

    @Override
    public void validate() {
        Ensure.paramNotNull(orderAmount, "充值金额为空");
        Ensure.param(orderAmount.compareTo(BigDecimal.ZERO)>=0, "充值金额不能低于1分钱");
    }
}
