package com.cgg.framework.enums;

public enum AppId {

    ANDROID(0, "安卓"),
    IOS(1, "IOS"),
    MINI_PROGRAM(2, "微信小程序"),
    H5(3, "H5"),
    PC(4, "WEB");

    int value;
    String description;

    AppId(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static boolean isIOS(Integer value) {
        return value != null && value.equals(IOS.value);
    }

    public static boolean isMiniProgram(Integer value) {
        return value != null && value.equals(MINI_PROGRAM.value);
    }

    public static boolean isH5(Integer value) {
        return value != null && value.equals(H5.value);
    }

    public static boolean isPC(Integer value) {
        return value != null && value.equals(PC.value);
    }

}
