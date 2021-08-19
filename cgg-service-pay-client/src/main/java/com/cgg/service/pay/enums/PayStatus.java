package com.cgg.service.pay.enums;

public enum PayStatus {

    UNPAID(0, "未支付"), PROCESSING(1, "处理中"), PAID(2, "已支付"), FAILED(3, "已失败");

    int value;
    String description;

    PayStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static boolean isProcessing(Integer value) {
        return value != null && value.equals(PROCESSING.value);
    }

    public static boolean isPaid(Integer value) {
        return value != null && value.equals(PAID.value);
    }

    public static boolean isFailed(Integer value) {
        return value != null && value.equals(FAILED.value);
    }

}
