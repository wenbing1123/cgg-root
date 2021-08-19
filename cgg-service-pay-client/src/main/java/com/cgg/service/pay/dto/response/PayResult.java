package com.cgg.service.pay.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

@Data
@Builder
public class PayResult {

    private String payGate;
    private String payOrderSn;
    private String orderStatus;
    @Singular("extend") private Map<String, String> extend;

}
