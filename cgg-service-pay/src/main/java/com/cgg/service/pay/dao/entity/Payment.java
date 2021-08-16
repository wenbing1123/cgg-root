package com.cgg.service.pay.dao.entity;

import com.cgg.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("t_payment")
public class Payment extends BaseEntity {

    @Column("store_name") private String storeName;
    @Column("site_name") private String siteName;
    @Column("site_order_sn") private String siteOrderSn;
    @Column("site_order_date") private LocalDateTime siteOrderDate;
    @Column("site_return_url") private String siteReturnUrl;
    @Column("site_return_attach") private String siteReturnAttach;
    @Column("account_name") private String accountName;
    @Column("user_ip") private String userIP;
    @Column("mer_id") private String merID;
    @Column("order_amount") private BigDecimal orderAmount;
    @Column("pay_order_sn") private String payOrderSn;
    @Column("order_status") private Integer orderStatus;
    @Column("pay_gate") private String payGate;
    @Column("sub_gate") private String subGate;
    @Column("pay_gate_date") private LocalDateTime payGateDate; // 发送网关时间
    @Column("bank_order_sn") private String bankOrderSn;
    @Column("bank_order_date") private LocalDateTime bankOrderDate;
    @Column("bank_status") private String bankStatus; //银行状态
    @Column("fail_reason") private String failReason; //银行失败原因
    @Column("bank_return_attach") private String bankReturnAttach;
    @Column("notify_flag") private Integer notifyFlag;

}
