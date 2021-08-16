package com.cgg.service.pay.dao.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@Table("t_payment_log")
public class PaymentLog {

    @Id private Long id;
    @Column("gmt_created") @CreatedDate private LocalDateTime gmtCreated;
    @Column("order_id") private Long orderId;
    @Column("order_status") private Integer orderStatus;
    @Column("order_attach") private String orderAttach;

}
