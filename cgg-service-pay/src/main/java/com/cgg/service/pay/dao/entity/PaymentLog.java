package com.cgg.service.pay.dao.entity;

import com.cgg.framework.dao.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@Table("t_payment_log")
public class PaymentLog extends Entity {

    @Column("gmt_created")
    @CreatedDate
    private LocalDateTime gmtCreated;
    @Column("order_id") private Long orderId;
    @Column("order_status") private Integer orderStatus;
    @Column("order_attach") private String orderAttach;

}
