package com.cgg.service.sms.dao.entity;

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
@Table("t_sms_log")
public class SmsLog extends BaseEntity {

    private String phone;
    @Column("tpl_code")
    private String tplCode;
    @Column("tpl_params")
    private String tplParams;
    @Column("result_code")
    private String resultCode; //发送结果
    @Column("result_msg")
    private String resultMsg; //发送结果信息
}
