package com.es.phoneshop.model.classes;

import com.es.phoneshop.model.interfaces.MessageCode;

public enum ApplicationMessage implements MessageCode {
    DEFAULT_CODE(-1),
    SUCCESS_HEAD(1),
    SUCCESS(2),
    ERROR_HEAD(3),
    NOT_NUMBER(4),
    EMPTY_FIELD(5),
    LESS_EQUAL_ZERO(6),
    NOT_ENOUGH(7),
    FRACTIONAL(8),
    NOT_FOUND(9),
    CART_UPDATE_SUCCESS(10),
    CART_ITEM_REMOVE_SUCCESS(11);

    private final Integer code;

    private ApplicationMessage(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
