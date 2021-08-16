package com.cgg.service.pay.service.dto.command;

import com.cgg.framework.dto.request.Command;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PrePayCmd extends Command {

    private String storeName;
    private String siteName;
    private String siteOrderSn;
    private LocalDateTime siteOrderDate;
    private String siteReturnUrl;
    private String siteReturnAttach;
    private BigDecimal orderAmount;
    private String accountName;
    private String userIP;
    private String merID;
    private String subMerID; //二级商户ID
    private String goodsType; //商品分类
    private String goodsName; // 商品名称
    private String payGate;
    private String subGate;
    @Singular("extend") private Map<String, String> extend;

}