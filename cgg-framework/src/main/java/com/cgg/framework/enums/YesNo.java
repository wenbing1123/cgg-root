package com.cgg.framework.enums;

public enum YesNo {

    YES(1, "是"), NO(0, "否");

    int value;
    String description;

    YesNo(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static boolean isYes(Integer value) {
        return value != null && value.equals(YES.value);
    }
}
