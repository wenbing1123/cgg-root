package com.cgg.service.pay.dto.response;

import com.cgg.framework.dto.response.ResponseData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PayResult extends ResponseData {

    private String payGate;
    private String payOrderSn;
    private String orderStatus;
    @Singular("extend") private Map<String, String> extend;

}
