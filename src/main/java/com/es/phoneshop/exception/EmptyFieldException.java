package com.es.phoneshop.exception;

public class EmptyFieldException extends Exception {
    public static final String EMPTY_FIELD_MESSAGE = "Input field is empty, please try to type a number";
    public EmptyFieldException(String s) {
        super(s);
    }
}
