package com.es.phoneshop.web.servlet;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.classes.CartItem;
import com.es.phoneshop.model.classes.CartService;
import com.es.phoneshop.model.interfaces.CartServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RemoveItemServlet extends HttpServlet {

    private CartServiceInterface cartService = CartService.getInstance();
    private final String PRODUCT_ID_ATTRIBUTE_NAME = "productId";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long productId = (Long) request.getAttribute(PRODUCT_ID_ATTRIBUTE_NAME);

        System.out.println(productId);

        try {
            cartService.remove(cartService.getCart(request),productId);
        } catch (CommonException e) {
            e.printStackTrace();
        }

        request.setAttribute(CartPageServlet.CART_ATTRIBUTE_NAME, cartService.getCart(request));
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
