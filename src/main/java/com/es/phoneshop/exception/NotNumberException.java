package com.es.phoneshop.exception;

public class NotNumberException extends Exception {
    public static final String NOT_NUMBER_MESSAGE = "Input string is not a number, please try to type a number";
    public NotNumberException(String s) {
        super(s);
    }
}
