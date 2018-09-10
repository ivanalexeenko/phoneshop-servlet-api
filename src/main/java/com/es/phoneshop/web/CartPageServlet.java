package com.es.phoneshop.web;

import com.es.phoneshop.additional.ArrayListVisitedPages;
import com.es.phoneshop.additional.VisitedPagesInterface;
import com.es.phoneshop.model.CartService;
import com.es.phoneshop.model.CartServiceInterface;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartPageServlet extends HttpServlet {
    private CartServiceInterface cartService = CartService.getInstance();
    private VisitedPagesInterface visitedPages = ArrayListVisitedPages.getInstance();
    public static final String CART_ATTRIBUTE_NAME = "cart";
    private static final int MAX_SESSION_TIMEOUT_IN_SECONDS = 10 * 20;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        visitedPages.saveAddress(request.getRequestURI());
        request.getSession().setMaxInactiveInterval(CartPageServlet.MAX_SESSION_TIMEOUT_IN_SECONDS);
        request.setAttribute(CartPageServlet.CART_ATTRIBUTE_NAME, cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }
}
