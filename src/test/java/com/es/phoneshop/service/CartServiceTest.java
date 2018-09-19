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
<<<<<<< HEAD
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
=======
>>>>>>> Task_2.5
import org.mockito.Mockito;

import javax.servlet.http.*;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartServiceInterface cartService;
    private Cart cart;
    private int stockOne = 162, stockTwo = 44, stockThree = 63, quantityOne = 98, quantityTwo = 44, quantityThree = 198;
    private int newQuantityOne = 10, newQuantityTwo = 170;
    private int timeOne = 900, intervalOne = 1000, timeTwo = 1000, intervalTwo = 900;
    private Long productIdOne = 7L, productIdTwo = 10L;
    private Product product;
    private Product productOne, productTwo, productThree;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void init() {
        cartService = CartService.getInstance();
        cart = new Cart();
        product = new Product();
        product.setStock(stockOne);
        product.setId(productIdOne);
        mockFields();
        setMockBehaviour();
    }

    @Test
<<<<<<< HEAD
    public void addProductQuantityLessEqualStockTest() throws ProductNotEnoughException {
=======
    public void addProductQuantityLessEqualStockTest() throws CommonException {
<<<<<<< HEAD
>>>>>>> Task_2.5
        cartService.add(cart,productOne,quantityOne);
        cartService.add(cart,productTwo,quantityTwo);
=======
        cartService.add(cart, productOne, quantityOne);
        cartService.add(cart, productTwo, quantityTwo);
>>>>>>> Task_2.5

        assertTrue(cart.getCartItems().get(0).equals(new CartItem(productOne, quantityOne)) && cart.getCartItems().get(1).equals(new CartItem(productTwo, quantityTwo)));
    }

<<<<<<< HEAD
    @Test(expected = ProductNotEnoughException.class)
    public void addProductQuantityGreaterStockTest() throws ProductNotEnoughException {
=======
    @Test(expected = CommonException.class)
    public void addProductQuantityGreaterStockTest() throws CommonException {
<<<<<<< HEAD
>>>>>>> Task_2.5
        cartService.add(cart,productThree,quantityThree);
=======
        cartService.add(cart, productThree, quantityThree);
>>>>>>> Task_2.5
    }

    @Test
<<<<<<< HEAD
    public void addEqualProductsTest() throws ProductNotEnoughException {
=======
    public void addEqualProductsTest() throws CommonException {
<<<<<<< HEAD
>>>>>>> Task_2.5
        cartService.add(cart,productOne,quantityTwo);
        cartService.add(cart,productOne,quantityTwo);
=======
        cartService.add(cart, productOne, quantityTwo);
        cartService.add(cart, productOne, quantityTwo);
>>>>>>> Task_2.5

        assertEquals(1, cart.getCartItems().size());
        assertEquals(cart.getCartItems().get(0).getProduct(), productOne);
    }

    @Test
<<<<<<< HEAD
    public void getCartCurrentSessionTest() throws ProductNotEnoughException, InterruptedException {
=======
    public void getCartCurrentSessionTest() throws CommonException {
<<<<<<< HEAD
>>>>>>> Task_2.5
        cartService.add(cart,productTwo,stockTwo);
        CartItem cartItem = new CartItem(productTwo,stockTwo);
        setIsNewIntervalBehaviour(timeOne,intervalOne);
=======
        cartService.add(cart, productTwo, quantityTwo);
        CartItem cartItem = new CartItem(productTwo, stockTwo);
        setIsNewIntervalBehaviour(timeOne, intervalOne);
>>>>>>> Task_2.5

        CartItem compareItem = cartService.getCart(request).getCartItems().get(0);

        assertEquals(compareItem, cartItem);
    }

    @Test
<<<<<<< HEAD
    public void getCartNewSessionTest() throws ProductNotEnoughException, InterruptedException {
=======
    public void getCartNewSessionTest() throws CommonException {
<<<<<<< HEAD
>>>>>>> Task_2.5
        cartService.add(cart,productOne,stockTwo);
        setIsNewIntervalBehaviour(timeTwo,intervalTwo);
=======
        cartService.add(cart, productOne, quantityTwo);
        setIsNewIntervalBehaviour(timeTwo, intervalTwo);
>>>>>>> Task_2.5

        Cart tempCart = cartService.getCart(request);

        assertTrue(tempCart.getCartItems().isEmpty());
    }

    @Test(expected = CommonException.class)
    public void updateStockLessQuantityTest() throws CommonException {
        cartService.add(cart, product, quantityOne);

        cartService.update(cart, product, newQuantityTwo);
    }

    @Test
    public void updateQuantityLessStockTest() throws CommonException {
        cartService.add(cart, product, quantityOne);
        Integer tempStock = product.getStock();

        cartService.update(cart, product, newQuantityOne);

        assertEquals((Integer) (tempStock - newQuantityOne + quantityOne), product.getStock());
    }

    @Test(expected = CommonException.class)
    public void updateNotExistTest() throws CommonException {
        cartService.add(cart, productTwo, quantityOne);

        cartService.update(cart, product, newQuantityOne);
    }

    @Test(expected = CommonException.class)
    public void removeNotExistTest() throws CommonException {
        cartService.add(cart, product, quantityOne);

        cartService.remove(cart, productIdTwo);
    }

    @Test
    public void removeExistTest() throws CommonException {
        cartService.add(cart, product, quantityOne);

        cartService.remove(cart, productIdOne);

        assertTrue(cart.getCartItems().isEmpty());
    }

    @After
    public void destroy() {
        cartService = null;
        cart = null;
        productOne = null;
        productTwo = null;
        productThree = null;
        product = null;
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
        Mockito.when(productTwo.getId()).thenReturn(productIdTwo);
    }

    private void setIsNewIntervalBehaviour(int time, int interval) {
        Mockito.when(session.getMaxInactiveInterval()).thenReturn(interval);
        boolean isNew = (time > session.getMaxInactiveInterval());
        Mockito.when(session.isNew()).thenReturn(isNew);
    }

}