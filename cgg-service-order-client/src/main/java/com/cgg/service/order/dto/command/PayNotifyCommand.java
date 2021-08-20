package com.cgg.service.order.dto.command;

import com.cgg.framework.dto.request.Command;
import com.cgg.framework.ensure.Ensure;
import com.cgg.framework.validate.Validate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PayNotifyCommand extends Command implements Validate {

    private String orderNo; //订单号

    @Override
    public void validate() {
        Ensure.paramNotBlank(orderNo, "订单号为空");
    }
}
