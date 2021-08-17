package com.cgg.service.pay.service.dto.command;

import com.cgg.framework.dto.request.Command;
import com.cgg.framework.ensure.Ensure;
import com.cgg.framework.validate.Validate;
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
public class PrePayCmd extends Command implements Validate {

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

    @Override
    public void validate() {
        Ensure.paramNotBlank(payGate, "网关为空");
        Ensure.paramNotBlank(storeName, "商户名为空");
        Ensure.paramNotBlank(siteName, "产品名为空");
        Ensure.paramNotNull(siteOrderDate, "产品订单号为空");
        Ensure.paramNotBlank(storeName, "商户名为空");
        Ensure.paramNotNull(orderAmount, "支付金额为空");
        Ensure.param(orderAmount.compareTo(BigDecimal.ZERO)>=0, "支付金额不能低于1分钱");
        Ensure.paramNotBlank(goodsName, "商品名为空");
    }

}