package com.cgg.service.order.dao.entity.base;

import com.cgg.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseOrder extends BaseEntity {

    private String orderNo; //订单编号
    private BigDecimal orderAmount; //订单金额

}
