package com.cgg.service.user.dao.entity;

import com.cgg.framework.entity.BaseEntity;
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
@Table("t_user_login_log")
public class UserLoginLog  extends BaseEntity {

    @Column("login_way")
    private Integer loginWay; //登录方式
    private String principal; //登录主体
    private String ip;
    private String device;
    private String agent;
    @Column("app_id")
    private String appId;
    @Column("app_version")
    private String appVersion;
    @Column("result_code")
    private String resultCode; //登录结果
    @Column("result_msg")
    private String resultMsg; //结果描述

}
