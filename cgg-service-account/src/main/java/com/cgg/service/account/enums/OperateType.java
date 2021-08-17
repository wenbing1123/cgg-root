package com.cgg.service.account.enums;

public enum OperateType {
    BALANCE_ADD(1,"余额增加"),
    BALANCE_CUT(2,"余额减少"),
    POINT_ADD(3,"积分增加"),
    POINT_CUT(4,"积分减少");

    int value;
    String description;

    OperateType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

}
