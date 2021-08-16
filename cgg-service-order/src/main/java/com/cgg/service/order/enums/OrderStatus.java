package com.cgg.service.order.enums;

public enum OrderStatus {

    WAITING_PAY(0, "待支付"),
    CANCELED(1, "已取消"),
    EXPIRED(2, "已过期"),
    WAITING_CHECK(3, "待审核"),
    WAITING_SHIP(4, "待发货"),
    WAITING_RECEIVE(5, "待收货"),
    COMPLETED(6, "已完成"),
    REFUND_PART(7, "已部分退货"),
    REFUND(8, "已全部退货");

    int value;
    String description;

    OrderStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static boolean isWaitingPay(Integer value) {
        return value != null && value.equals(WAITING_PAY.value);
    }

    public static boolean isCanceled(Integer value) {
        return value != null && value.equals(CANCELED.value);
    }

    public static boolean isExpired(Integer value) {
        return value != null && value.equals(EXPIRED.value);
    }

    public static boolean isWaitingCheck(Integer value) {
        return value != null && value.equals(WAITING_CHECK.value);
    }

    public static boolean isWaitingShip(Integer value) {
        return value != null && value.equals(WAITING_SHIP.value);
    }

    public static boolean isWaitingReceive(Integer value) {
        return value != null && value.equals(WAITING_RECEIVE.value);
    }

    public static boolean isCompleted(Integer value) {
        return value != null && value.equals(COMPLETED.value);
    }

    public static boolean isRefundPart(Integer value) {
        return value != null && value.equals(REFUND_PART.value);
    }

    public static boolean isRefund(Integer value) {
        return value != null && value.equals(REFUND.value);
    }

}
