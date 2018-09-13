package com.es.phoneshop.web.servlet;
import com.es.phoneshop.additional.ArrayListVisitedPages;
import com.es.phoneshop.additional.VisitedPagesInterface;
import com.es.phoneshop.exception.*;
import com.es.phoneshop.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;

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

        getReady(request);

        Product product = null;
        try {
            product = productDao.getProduct(getProductId(request));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ProductNotFoundException e1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        setAttributes(request,product);
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getReady(request);

        Long productId = getProductId(request);
        Product product = null;
        message = null;
        quantityString = request.getParameter("quantity");
        try {
            executeSuccessfulOperations(request,product,productId);
        }
        catch (EmptyFieldException | NotNumberException | LessEqualZeroAmountException | FractionalQuantityException | ProductNotEnoughException e) {
            message = e.getMessage();
        } catch (ProductNotFoundException e1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        try {
            product = productDao.getProduct(productId);
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.sendRedirect(request.getRequestURI());
    }
    private void getReady(HttpServletRequest request) throws UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");
        visitedPages.saveAddress(request);
        clearInputIfNeeded();
    }
    private Integer parseAttribute(HttpServletRequest request,String attributeString) throws EmptyFieldException, NotNumberException, LessEqualZeroAmountException, FractionalQuantityException {

        if(attributeString == null ||attributeString.isEmpty()) {
            throw new EmptyFieldException
                    (EmptyFieldException.EMPTY_FIELD_MESSAGE);
        }
        Integer quantity = null;
        try {
            char test = attributeString.charAt(attributeString.length() - 1);
            int numberTest = Integer.parseInt(String.valueOf(test));
            Double tempDouble =  DecimalFormat.getNumberInstance(request.getLocale()).parse(attributeString).doubleValue();
            Integer tempInteger = tempDouble.intValue();
            if(!tempDouble.equals(tempInteger.doubleValue())) {
                throw new FractionalQuantityException(FractionalQuantityException.FRACTIONAL_QUANTITY_MESSAGE);
            }
            quantity =  tempInteger;
        }
        catch (NumberFormatException | ParseException e) {
            throw new NotNumberException(NotNumberException.NOT_NUMBER_MESSAGE);
        }
        if(quantity <= 0) {
            throw new LessEqualZeroAmountException(LessEqualZeroAmountException.LESS_EQUAL_ZERO_AMOUNT_MESSAGE);
        }
        return quantity;
    }
    private void clearInputIfNeeded() {

        if(visitedPages.isLastAddressNew()) {
            message = null;
            quantityString = null;
        }
    }
    private void setAttributes(HttpServletRequest request,Product product) {

        request.setAttribute("notEnough",ProductNotEnoughException.PRODUCT_NOT_ENOUGH_MESSAGE);
        request.setAttribute("notNumber",NotNumberException.NOT_NUMBER_MESSAGE);
        request.setAttribute("lessEqualZero",LessEqualZeroAmountException.LESS_EQUAL_ZERO_AMOUNT_MESSAGE);
        request.setAttribute("emptyField",EmptyFieldException.EMPTY_FIELD_MESSAGE);
        request.setAttribute("fractional",FractionalQuantityException.FRACTIONAL_QUANTITY_MESSAGE);
        request.setAttribute(ProductDetailsPageServlet.PRODUCT_ATTRIBUTE_NAME,product);
        request.setAttribute(ProductDetailsPageServlet.MESSAGE_ATTRIBUTE_NAME,message);
        request.setAttribute("quantity",quantityString);
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
    private void executeSuccessfulOperations(HttpServletRequest request,Product product,Long productId) throws ProductNotFoundException, NotNumberException, FractionalQuantityException, LessEqualZeroAmountException, EmptyFieldException, ProductNotEnoughException {

        Integer quantity = parseAttribute(request,quantityString);
        product = productDao.getProduct(productId);
        cartService.add(cartService.getCart(request),product,quantity);
        message = ProductDetailsPageServlet.SUCCESS_MESSAGE;
    }
}