package com.cgg.service.order.controller.v1;

import com.cgg.service.order.api.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("v1/purchases")
@Tag(name = "购买服务")
public class PurchaseController {

    @Resource
    private PurchaseService purchaseService;

    @PostMapping("pre_order")
    @Operation(description = "预下单")
    public void preOrder() {
    }

    @PostMapping("place_order")
    @Operation(description = "下单")
    public void placeOrder() {
        
    }

    @PostMapping("pre_pay")
    @Operation(description = "订单预支付")
    public void orderPrePay() {

    }

    @DeleteMapping("cancel")
    @Operation(description = "订单取消")
    public void orderCancel() {

    }

}
