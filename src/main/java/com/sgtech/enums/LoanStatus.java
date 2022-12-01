package com.sgtech.enums;

public enum LoanStatus {
    NEW,
    PARTIALLY_PAID,
    CLOSED,
    OVERDUE;

    private String value;

    public String getValue() {
        return this.value;
    }

}
