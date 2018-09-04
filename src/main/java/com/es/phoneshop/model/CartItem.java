package com.es.phoneshop.model;

import java.util.Objects;

public class CartItem {
    private Product product;
    private Integer quantity;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public CartItem() {
        this.product = new Product();
        this.quantity = 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.product,this.quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem)obj;
        return ((this.product == null && cartItem.product == null) || (this.product != null && this.product.equals(cartItem.product)))
                && ((this.quantity == null && cartItem.quantity == null) || (this.quantity != null && this.quantity.equals(cartItem.quantity)));
    }
}
