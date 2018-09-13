package com.es.phoneshop.exception;

public class LessEqualZeroAmountException extends Exception {

    public static final String LESS_EQUAL_ZERO_AMOUNT_MESSAGE = "Quantity should be a positive number, please try to type a number > 0";

    public LessEqualZeroAmountException(String s) {
        super(s);
    }
}
