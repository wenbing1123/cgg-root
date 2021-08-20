package com.cgg.service.order.factory;

import com.cgg.service.order.api.OrderService;

public interface OrderServiceFactory {

    OrderService getOrderService(String name);

}
