package com.es.phoneshop.exception;

public class FractionalQuantityException extends Exception {

    public static final String FRACTIONAL_QUANTITY_MESSAGE = "Input quantity is not an integer number, fractional found";

    public FractionalQuantityException(String s) {
        super(s);
    }
}
