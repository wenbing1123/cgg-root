package com.cgg.service.order.enums;

public enum RechargeStatus {
    UNPAID(0, "未支付"),
    PAYING(2, "支付中"),
    PAID(3, "已支付"),
    FAILED(4, "已失败");

    int value;
    String description;

    RechargeStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static boolean isUnPaid(Integer value) {
        return value != null && value.equals(UNPAID.value);
    }

}
