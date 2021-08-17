package com.cgg.service.order.dao.entity;

import com.cgg.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("t_recharge_order")
public class RechargeOrder extends BaseEntity {

    @Column("user_id") private Long userId; //用户编号
    @Column("order_no") private String orderNo; //订单编号
    @Column("order_amount") private BigDecimal orderAmount; //订单金额
    @Column("order_status") private Integer orderStatus; //订单状态

}
