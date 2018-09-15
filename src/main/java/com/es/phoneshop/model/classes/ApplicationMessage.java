package com.es.phoneshop.model.classes;

import com.es.phoneshop.model.interfaces.MessageCode;

public enum ApplicationMessage implements MessageCode {
    DEFAULT_CODE(-1),
    SUCCESS_HEAD(1),
    SUCCESS(2),
    ERROR_HEAD(3),
    NOT_NUMBER(4),
    EMPTY_FIELD(5),
    LESS_EQUAL_ZERO(5),
    NOT_ENOUGH(6),
    FRACTIONAL(7),
    NOT_FOUND(8);

    private final Integer code;

    private ApplicationMessage(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
