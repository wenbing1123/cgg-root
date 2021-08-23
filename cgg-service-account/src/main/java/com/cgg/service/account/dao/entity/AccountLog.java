package com.cgg.service.account.dao.entity;

import com.cgg.framework.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@Table("t_account_log")
public class AccountLog extends Entity {

    @Column("gmt_created")
    @CreatedDate
    private LocalDateTime gmtCreated;
    @Column("account_id")
    private Long accountId;
    @Column("operate_type")
    private Integer operateType;
    @Column("ref_id")
    private String refId;
    private BigDecimal value;
    private String remark;

}
