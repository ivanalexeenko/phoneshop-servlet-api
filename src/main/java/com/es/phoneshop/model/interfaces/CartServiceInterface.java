package com.es.phoneshop.model.interfaces;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.classes.Cart;
import com.es.phoneshop.model.classes.Product;

import javax.servlet.http.HttpServletRequest;

public interface CartServiceInterface {

    public Cart getCart(HttpServletRequest request);

    public void add(Cart cart, Product product, Integer quantity) throws CommonException;

    public void update(Cart cart, Product product, Integer quantity) throws CommonException;

    public void remove(Cart cart,Long productId) throws CommonException;
}
