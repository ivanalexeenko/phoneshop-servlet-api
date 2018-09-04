package com.es.phoneshop.model;

import com.es.phoneshop.exception.ProductNotEnoughException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CartService {
    private static final String CART_ATTRIBUTE_NAME = "cart";
    private static class CartServiceHelper {
        private static final CartService INSTANCE = new CartService();
    }
    private boolean isInitialized = false;
    public static CartService getInstance() {
        return CartServiceHelper.INSTANCE;
    }

    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CartService.CART_ATTRIBUTE_NAME);
        if(cart == null) {
            cart = new Cart();
            if(!isInitialized) {
                pushDefaultItems(cart);
            }
            isInitialized = true;
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
        cart.getCartItems().add(new CartItem(product,quantity));
    }
    private void pushDefaultItems(Cart cart) {
        ProductDao productDao = ArrayListProductDao.getInstance();
        productDao.findProducts().forEach(product -> {
            try {
                add(cart,product,1);
            } catch (ProductNotEnoughException ignored) {

            }
        });
    }
}
