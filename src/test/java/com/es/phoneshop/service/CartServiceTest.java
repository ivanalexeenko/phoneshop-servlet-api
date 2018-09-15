package com.es.phoneshop.service;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.classes.Cart;
import com.es.phoneshop.model.classes.CartItem;
import com.es.phoneshop.model.classes.CartService;
import com.es.phoneshop.model.classes.Product;
import com.es.phoneshop.model.interfaces.CartServiceInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.*;

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
    public void addProductQuantityLessEqualStockTest() throws CommonException {
        cartService.add(cart,productOne,quantityOne);
        cartService.add(cart,productTwo,quantityTwo);

        assertTrue(cart.getCartItems().get(0).equals(new CartItem(productOne,quantityOne)) && cart.getCartItems().get(1).equals(new CartItem(productTwo,quantityTwo)));
    }

    @Test(expected = CommonException.class)
    public void addProductQuantityGreaterStockTest() throws CommonException {
        cartService.add(cart,productThree,quantityThree);
    }

    @Test
    public void addEqualProductsTest() throws CommonException {
        cartService.add(cart,productOne,quantityTwo);
        cartService.add(cart,productOne,quantityTwo);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(cart.getCartItems().get(0).getProduct(),productOne);
    }

    @Test
    public void getCartCurrentSessionTest() throws CommonException {
        cartService.add(cart,productTwo,stockTwo);
        CartItem cartItem = new CartItem(productTwo,stockTwo);
        setIsNewIntervalBehaviour(timeOne,intervalOne);

        CartItem compareItem = cartService.getCart(request).getCartItems().get(0);

        assertEquals(compareItem,cartItem);
    }

    @Test
    public void getCartNewSessionTest() throws CommonException {
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