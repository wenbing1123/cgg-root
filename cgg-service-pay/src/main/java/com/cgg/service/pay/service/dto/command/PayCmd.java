package com.cgg.service.pay.service.dto.command;

import com.cgg.framework.dto.request.Command;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PayCmd extends Command {

    private String payGate;
    private String subGate;
    private String payOrderSn;
    @Singular("extend") private Map<String,String> extend;

}
