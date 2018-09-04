package com.es.phoneshop.service;

import com.es.phoneshop.exception.ProductNotEnoughException;
import com.es.phoneshop.model.*;
import com.es.phoneshop.web.CartPageServlet;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartService cartService;
    private Cart cart;
    private final int stocksAndQuantities[] = {162,44,63,98,44,198};
    @Before
    public void init() {
        cartService = CartService.getInstance();
        cart = new Cart();
    }
    @Test
    public void addProductQuantityLessEqualStockTest() throws ProductNotEnoughException {
        int stockOne = stocksAndQuantities[0],
                quantityOne = stocksAndQuantities[3],
                stockTwo = stocksAndQuantities[1],
                quantityTwo = stocksAndQuantities[4];
        Product productOne = new Product();
        productOne.setStock(stockOne);
        Product productTwo = new Product();
        productTwo.setStock(stockTwo);

            cartService.add(cart,productOne,quantityOne);
            cartService.add(cart,productTwo,quantityTwo);
            ArrayList<CartItem> list = (ArrayList<CartItem>) cart.getCartItems();
            assertTrue(list.get(0).equals(new CartItem(productOne,quantityOne)) && list.get(1).equals(new CartItem(productTwo,quantityTwo)));
    }
    @Test(expected = ProductNotEnoughException.class)
    public void addProductQuantityGreaterStockTest() throws ProductNotEnoughException {
        int stock = stocksAndQuantities[2],
                quantity = stocksAndQuantities[5];
        Product product = new Product();
        product.setStock(stock);
        cartService.add(cart,product,quantity);
    }

    @Test
    public void getCartCurrentSessionTest() throws ProductNotEnoughException, InterruptedException {
        Product product = new Product();
        product.setStock(stocksAndQuantities[0]);
        cartService.add(cart,product,stocksAndQuantities[1]);
        CartItem cartItem = new CartItem(product,stocksAndQuantities[1]);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        int time = 900;
        int interval = 1000;
        session.setMaxInactiveInterval(interval);
        boolean isOver = (time > session.getMaxInactiveInterval());
        Mockito.when(session.isNew()).thenReturn(!isOver);
        Mockito.when(session.getAttribute("cart")).thenReturn(cart);
        session.setAttribute("cart",cart);
        Cart tempCart = cartService.getCart(request);
        CartItem compareItem = tempCart.getCartItems().get(0);
        assertEquals(compareItem,cartItem);
    }
    @Test
    public void getCartNewSessionTest() throws ProductNotEnoughException, InterruptedException {
        Product product = new Product();
        product.setStock(stocksAndQuantities[0]);
        cartService.add(cart,product,stocksAndQuantities[1]);
        CartItem cartItem = new CartItem(product,stocksAndQuantities[1]);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        int time = 1000;
        int interval = 900;
        session.setMaxInactiveInterval(interval);
        boolean isOver = (time > session.getMaxInactiveInterval());
        Mockito.when(session.isNew()).thenReturn(isOver);
        Mockito.when(session.getAttribute("cart")).thenReturn(cart);
        session.setAttribute("cart",cart);
        Cart tempCart = cartService.getCart(request);
        assertTrue(tempCart.getCartItems().isEmpty());
    }
    @After
    public void destroy() {
        cartService = null;
        cart = null;
    }
}