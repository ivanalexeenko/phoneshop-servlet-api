package com.es.phoneshop.web;
import com.es.phoneshop.additional.ArrayListVisitedPages;
import com.es.phoneshop.additional.VisitedPagesInterface;
import com.es.phoneshop.exception.*;
import com.es.phoneshop.model.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private CartServiceInterface cartService = CartService.getInstance();
    private VisitedPagesInterface visitedPages = ArrayListVisitedPages.getInstance();
    private static final String PRODUCT_ATTRIBUTE_NAME = "product";
    private static final String MESSAGE_ATTRIBUTE_NAME = "message";
    public static final String SUCCESS_MESSAGE = "Product Added to Cart Successfully!";
    private String message = null;
    private String quantityString = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        visitedPages.saveAddress(request.getRequestURI());
        if(visitedPages.isLastAddressNew()) {
            message = null;
            quantityString = null;
        }
        Product product = null;
        try {
            product = productDao.getProduct(getProductId(request));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ProductNotFoundException e1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        request.setAttribute(ProductDetailsPageServlet.PRODUCT_ATTRIBUTE_NAME,product);
        request.setAttribute(ProductDetailsPageServlet.MESSAGE_ATTRIBUTE_NAME,message);
        request.setAttribute("quantity",quantityString);
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        visitedPages.saveAddress(request.getRequestURI());
        Long productId = getProductId(request);
        Product product = null;
        message = null;
        quantityString = request.getParameter("quantity");
        try {
            if(quantityString == null || quantityString.isEmpty()) {
                throw new EmptyFieldException(EmptyFieldException.EMPTY_FIELD_MESSAGE);
            }
            Integer quantity = null;
            try {
               quantity = Integer.valueOf(quantityString);
            }
            catch (NumberFormatException e) {
                throw new NotNumberException(NotNumberException.NOT_NUMBER_MESSAGE);
            }
            if(quantity <= 0) {
                throw new LessEqualZeroAmountException(LessEqualZeroAmountException.LESS_EQUAL_ZERO_AMOUNT_MESSAGE);
            }
            product = productDao.getProduct(productId);
            cartService.add(cartService.getCart(request),product,quantity);
            message = ProductDetailsPageServlet.SUCCESS_MESSAGE;
        }
        catch (EmptyFieldException | NotNumberException | LessEqualZeroAmountException e) {
            message = e.getMessage();
        } catch (ProductNotFoundException e1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ProductNotEnoughException e2) {
            message = ProductNotEnoughException.PRODUCT_NOT_ENOUGH_MESSAGE;
        }
        try {
            product = productDao.getProduct(productId);
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.sendRedirect(request.getRequestURI());
    }

    private Long getProductId(HttpServletRequest request) throws NumberFormatException,StringIndexOutOfBoundsException {
        String path = request.getPathInfo();
        if(path == null || path.compareTo("/") == 0) {
            throw new StringIndexOutOfBoundsException();
        }
        int index = path.indexOf("/") + 1;
        path = path.substring(index);
        return Long.valueOf(path);
    }
}