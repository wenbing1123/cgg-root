package com.cgg.service.order.enums;

public enum OrderStatus {

    UNPAID(0, "未支付"),
    CANCELED(1, "已取消"),
    EXPIRED(2, "已过期"),
    PAID(3, "已支付"),
    CHECKED(4, "已审核"),
    SHIPPED(5, "已发货"),
    COMPLETED(6, "已完成"),
    EVALUATED(7, "已评价"),
    REFUND(8, "已退货");

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

    public static boolean isUnPaid(Integer value) {
        return value != null && value.equals(UNPAID.value);
    }

    public static boolean isCanceled(Integer value) {
        return value != null && value.equals(CANCELED.value);
    }

    public static boolean isExpired(Integer value) {
        return value != null && value.equals(EXPIRED.value);
    }

    public static boolean isPaid(Integer value) {
        return value != null && value.equals(PAID.value);
    }

    public static boolean isChecked(Integer value) {
        return value != null && value.equals(CHECKED.value);
    }

    public static boolean isShipped(Integer value) {
        return value != null && value.equals(SHIPPED.value);
    }

    public static boolean isCompleted(Integer value) {
        return value != null && value.equals(COMPLETED.value);
    }

    public static boolean isEvaluated(Integer value) {
        return value != null && value.equals(EVALUATED.value);
    }

    public static boolean isRefund(Integer value) {
        return value != null && value.equals(REFUND.value);
    }

}
