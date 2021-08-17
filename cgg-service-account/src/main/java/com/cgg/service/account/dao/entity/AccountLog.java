package com.cgg.service.account.dao.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@Table("t_account_log")
public class AccountLog {

    @Id
    private Long id;
    @Column("gmt_created") @CreatedDate
    private LocalDateTime gmtCreated;
    @Column("account_id") private Long accountId;
    @Column("operate_type") private Integer operateType;
    @Column("ref_id") private String refId;
    private BigDecimal value;
    private String remark;

}
