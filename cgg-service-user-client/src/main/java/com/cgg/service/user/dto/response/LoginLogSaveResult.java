package com.cgg.service.user.dto.response;

import com.cgg.framework.dto.response.ResponseData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class LoginLogSaveResult extends ResponseData {

    private Integer loginWay; //登录方式
    private String principal; //登录主体
    private String ip;
    private String device;
    private String agent;
    private String appId;
    private String appVersion;
    private String resultCode; //登录结果
    private String resultMsg; //结果描述

}
