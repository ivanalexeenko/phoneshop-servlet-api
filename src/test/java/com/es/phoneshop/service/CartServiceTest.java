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

    @Before
    public void init() {
        cartService = CartService.getInstance();
        cart = new Cart();
    }

    @Test
    public void addProductQuantityLessEqualStockTest() throws ProductNotEnoughException {
        Product productOne = Mockito.mock(Product.class);
        Product productTwo = Mockito.mock(Product.class);
        Mockito.when(productOne.getStock()).thenReturn(stockOne);
        Mockito.when(productTwo.getStock()).thenReturn(stockTwo);
        cartService.add(cart,productOne,quantityOne);
        cartService.add(cart,productTwo,quantityTwo);
        ArrayList<CartItem> list = (ArrayList<CartItem>) cart.getCartItems();
        assertTrue(list.get(0).equals(new CartItem(productOne,quantityOne)) && list.get(1).equals(new CartItem(productTwo,quantityTwo)));
    }

    @Test(expected = ProductNotEnoughException.class)
    public void addProductQuantityGreaterStockTest() throws ProductNotEnoughException {
        Product product = Mockito.mock(Product.class);
        product.setStock(stockThree);
        cartService.add(cart,product,quantityThree);
    }

    @Test
    public void addEqualProductsTest() throws ProductNotEnoughException {
        Product productOne = Mockito.mock(Product.class);
        Product productTwo = productOne;
        Mockito.when(productOne.getStock()).thenReturn(stockOne);
        Mockito.when(productTwo.getStock()).thenReturn(stockOne);
        productOne.setStock(stockOne);
        productTwo.setStock(stockOne);
        cartService.add(cart,productOne,quantityTwo);
        cartService.add(cart,productTwo,quantityTwo);
        assertEquals(1, cart.getCartItems().size());
        assertEquals(cart.getCartItems().get(0).getProduct(),productOne);
    }

    @Test
    public void getCartCurrentSessionTest() throws ProductNotEnoughException, InterruptedException {
        Product product = Mockito.mock(Product.class);
        Mockito.when(product.getStock()).thenReturn(stockOne);
        product.setStock(stockOne);
        cartService.add(cart,product,stockTwo);
        CartItem cartItem = new CartItem(product,stockTwo);
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
        Product product = Mockito.mock(Product.class);
        Mockito.when(product.getStock()).thenReturn(stockOne);
        cartService.add(cart,product,stockTwo);
        CartItem cartItem = new CartItem(product,stockTwo);
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