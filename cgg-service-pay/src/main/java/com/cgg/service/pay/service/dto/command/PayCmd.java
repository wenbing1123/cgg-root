package com.cgg.service.pay.service.dto.command;

import com.cgg.framework.dto.request.Command;
import com.cgg.framework.ensure.Ensure;
import com.cgg.framework.validate.Validate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PayCmd extends Command implements Validate {

    private String payGate;
    private String subGate;
    private String payOrderSn;
    @Singular("extend") private Map<String,String> extend;

    @Override
    public void validate() {
        Ensure.paramNotBlank(payGate, "网关为空");
        Ensure.paramNotBlank(payOrderSn, "支付订单号为空");
    }
}
