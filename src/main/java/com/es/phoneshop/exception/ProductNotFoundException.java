package com.es.phoneshop.exception;

public class ProductNotFoundException extends Exception {

    public static final String productNotFoundMessage = "Product with current id does not exist";

    public ProductNotFoundException(String string) {
        super(string);
    }
}
