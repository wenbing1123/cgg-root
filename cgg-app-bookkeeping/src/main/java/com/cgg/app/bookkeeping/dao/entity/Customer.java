package com.cgg.app.bookkeeping.dao.entity;

import com.cgg.framework.dao.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("t_customer")
public class Customer extends BaseEntity {

    private String name; // 客户名称
    @Column("customer_type")
    private Integer customerType; //客户类型
    @Column("contact_person")
    private String contactPerson; //联系人
    @Column("contact_phone")
    private String contactPhone; //联系电话
    @Column("contact_address")
    private String contactAddress; //联系电话

}
