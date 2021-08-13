package com.cgg.app.bookkeeping.enums;

public enum CustomerType {

    COMPANY(0, "企业"), PERSONAL(1, "个人");

    int value;
    String description;

    CustomerType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static boolean isCompany(Integer value) {
        return value != null && value.equals(COMPANY.value);
    }

    public static boolean isPersonal(Integer value) {
        return value != null && value.equals(PERSONAL.value);
    }

}
