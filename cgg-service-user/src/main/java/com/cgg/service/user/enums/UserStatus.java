package com.cgg.service.user.enums;

public enum UserStatus {
    NORMAL(0, "正常"), LOCKED(1, "锁定"), DISABLED(2, "禁用");

    int value;
    String description;

    UserStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static boolean isNormal(Integer value) {
        return value != null && value.equals(NORMAL.value);
    }

    public static boolean isLocked(Integer value) {
        return value != null && value.equals(LOCKED.value);
    }

    public static boolean isDisabled(Integer value) {
        return value != null && value.equals(DISABLED.value);
    }
}
