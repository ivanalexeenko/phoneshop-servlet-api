package com.es.phoneshop.model;

import com.es.phoneshop.exception.ProductNotEnoughException;

import javax.servlet.http.HttpServletRequest;

public interface CartServiceInterface {

    public Cart getCart(HttpServletRequest request);

    public void add(Cart cart,Product product,Integer quantity) throws ProductNotEnoughException;
}
