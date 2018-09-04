package com.es.phoneshop.exception;

public class ProductNotEnoughException extends Exception {
    public static final String PRODUCT_NOT_ENOUGH_MESSAGE = "Sorry, we do not have such amount of this product in stock now";
    public ProductNotEnoughException(String s) {
        super(s);
    }
}
