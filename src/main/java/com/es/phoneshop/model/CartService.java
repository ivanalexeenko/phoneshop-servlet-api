package com.es.phoneshop.model;

import com.es.phoneshop.exception.ProductNotEnoughException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CartService implements CartServiceInterface {

    public static final String CART_ATTRIBUTE_NAME = "cart";

    private static class CartServiceHelper {
        private static final CartService INSTANCE = new CartService();
    }
    public static CartService getInstance() {
        return CartServiceHelper.INSTANCE;
    }

    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CartService.CART_ATTRIBUTE_NAME);
        if(cart == null) {
            cart = new Cart();
            session.setAttribute(CartService.CART_ATTRIBUTE_NAME,cart);
        }
        if(session.isNew()) {
            return new Cart();
        }
        return cart;
    }

    public void add(Cart cart,Product product,Integer quantity) throws ProductNotEnoughException {
        if(product.getStock() < quantity) {
            throw new ProductNotEnoughException(ProductNotEnoughException.PRODUCT_NOT_ENOUGH_MESSAGE);
        }
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream().filter(cartItem -> cartItem.getProduct().equals(product)).findAny();
        if(!optionalCartItem.isPresent()) {
            product.setStock(product.getStock() - quantity);
            cart.getCartItems().add(new CartItem(product,quantity));
        }
        else {
            optionalCartItem = optionalCartItem.filter(cartItem -> quantity <= cartItem.getProduct().getStock());
            if(!optionalCartItem.isPresent()) {
                throw new ProductNotEnoughException(ProductNotEnoughException.PRODUCT_NOT_ENOUGH_MESSAGE);
            }
            product.setStock(product.getStock() - quantity);
            optionalCartItem.get().setProduct(product);
            optionalCartItem.get().setQuantity(optionalCartItem.get().getQuantity() + quantity);
        }
    }
}
