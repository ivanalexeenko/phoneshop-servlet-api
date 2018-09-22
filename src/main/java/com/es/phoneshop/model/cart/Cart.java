package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private List <CartItem> cartItems;

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
