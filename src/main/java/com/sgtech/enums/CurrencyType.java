package com.sgtech.enums;

public enum CurrencyType {
    US_DOLLAR,
    SING_DOLLAR,
    EURO,
    YEN,
    VND;
    private String value;

    public String getValue() {
        return this.value;
    }
}
