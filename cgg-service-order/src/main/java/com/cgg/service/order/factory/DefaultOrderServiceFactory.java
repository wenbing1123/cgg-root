package com.cgg.service.order.factory;

import com.cgg.framework.exception.SysException;
import com.cgg.service.order.api.OrderService;
import com.cgg.service.order.api.PurchaseService;
import com.cgg.service.order.api.RechargeService;
import com.cgg.service.order.constants.OrderConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class DefaultOrderServiceFactory implements OrderServiceFactory {

    @Resource
    private RechargeService rechargeService;

    @Resource
    private PurchaseService purchaseService;

    @Override
    public OrderService getOrderService(String name) {
        if (StringUtils.startsWith(name, OrderConstants.ORDER_NO_PREFIX_RECHARGE)) {
            return rechargeService;
        }

        if (StringUtils.startsWith(name, OrderConstants.ORDER_NO_PREFIX_RECHARGE)) {
            return purchaseService;
        }

        throw new SysException("未知订单");
    }


}
