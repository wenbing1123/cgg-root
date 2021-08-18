package com.cgg.service.order.dto.response;

import com.cgg.framework.dto.response.ResponseData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RechargePlaceOrderResult extends ResponseData {

    private String orderNo;

}
