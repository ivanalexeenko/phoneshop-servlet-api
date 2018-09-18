package com.es.phoneshop.exception;

import com.es.phoneshop.model.classes.ApplicationMessage;
import com.es.phoneshop.model.interfaces.MessageCode;

public class CommonException extends Exception implements MessageCode {

    private ApplicationMessage message;

    public CommonException(ApplicationMessage message) {
        super();
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.message.getCode();
    }
}
