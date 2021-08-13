package com.cgg.framework.enums;

public enum Status {

    SUCCESS(0, "成功"),
    ERROR(-1, "错误");

    int value;
    String description;

    Status(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static boolean isSuccess(Integer value) {
        return value != null && value.equals(SUCCESS.value);
    }

}
