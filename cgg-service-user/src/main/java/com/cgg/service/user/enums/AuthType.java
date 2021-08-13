package com.cgg.service.user.enums;

public enum AuthType {
    USERNAME(0, "用户名"),
    PHONE(1, "手机号"),
    WECHAT(2, "微信");

    int value;
    String description;

    AuthType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static boolean isUsername(Integer value) {
        return value != null && value.equals(USERNAME.value);
    }

    public static boolean isPhone(Integer value) {
        return value != null && value.equals(PHONE.value);
    }

    public static boolean isWechat(Integer value) {
        return value != null && value.equals(WECHAT.value);
    }
}
