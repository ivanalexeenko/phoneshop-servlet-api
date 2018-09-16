package com.es.phoneshop.service;

import com.es.phoneshop.exception.ProductNotEnoughException;
import com.es.phoneshop.model.*;
import com.es.phoneshop.web.CartPageServlet;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.servlet.http.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartServiceInterface cartService;
    private Cart cart;
    private final int stockOne = 162,stockTwo = 44,stockThree = 63,quantityOne = 98,quantityTwo = 44,quantityThree = 198;
    private final int timeOne = 900, intervalOne = 1000,timeTwo = 1000,intervalTwo = 900;
    private Product productOne,productTwo,productThree;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void init() {
        cartService = CartService.getInstance();
        cart = new Cart();

        mockFields();
        setMockBehaviour();
    }

    @Test
    public void addProductQuantityLessEqualStockTest() throws ProductNotEnoughException {
        cartService.add(cart,productOne,quantityOne);
        cartService.add(cart,productTwo,quantityTwo);

        assertTrue(cart.getCartItems().get(0).equals(new CartItem(productOne,quantityOne)) && cart.getCartItems().get(1).equals(new CartItem(productTwo,quantityTwo)));
    }

    @Test(expected = ProductNotEnoughException.class)
    public void addProductQuantityGreaterStockTest() throws ProductNotEnoughException {
        cartService.add(cart,productThree,quantityThree);
    }

    @Test
    public void addEqualProductsTest() throws ProductNotEnoughException {
        cartService.add(cart,productOne,quantityTwo);
        cartService.add(cart,productOne,quantityTwo);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(cart.getCartItems().get(0).getProduct(),productOne);
    }

    @Test
    public void getCartCurrentSessionTest() throws ProductNotEnoughException, InterruptedException {
        cartService.add(cart,productTwo,stockTwo);
        CartItem cartItem = new CartItem(productTwo,stockTwo);
        setIsNewIntervalBehaviour(timeOne,intervalOne);

        CartItem compareItem = cartService.getCart(request).getCartItems().get(0);

        assertEquals(compareItem,cartItem);
    }

    @Test
    public void getCartNewSessionTest() throws ProductNotEnoughException, InterruptedException {
        cartService.add(cart,productOne,stockTwo);
        setIsNewIntervalBehaviour(timeTwo,intervalTwo);

        Cart tempCart = cartService.getCart(request);

        assertTrue(tempCart.getCartItems().isEmpty());
    }

    @After
    public void destroy() {
        cartService = null;
        cart = null;
        productOne = null;
        productTwo = null;
        productThree = null;
    }

    private void mockFields() {
        productOne = Mockito.mock(Product.class);
        productTwo = Mockito.mock(Product.class);
        productThree = Mockito.mock(Product.class);
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
    }

    private void setMockBehaviour() {
        Mockito.when(productOne.getStock()).thenReturn(stockOne);
        Mockito.when(productTwo.getStock()).thenReturn(stockTwo);
        Mockito.when(productThree.getStock()).thenReturn(stockThree);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute(CartService.CART_ATTRIBUTE_NAME)).thenReturn(cart);
    }
    private void setIsNewIntervalBehaviour(int time,int interval) {
        Mockito.when(session.getMaxInactiveInterval()).thenReturn(interval);
        boolean isNew = (time > session.getMaxInactiveInterval());
        Mockito.when(session.isNew()).thenReturn(isNew);
    }
}