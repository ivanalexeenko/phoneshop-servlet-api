package com.es.phoneshop.web.servlet;
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

import static javax.swing.text.html.CSS.getAttribute;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao = ArrayListProductDao.getInstance();
    private CartServiceInterface cartService = CartService.getInstance();
    private static final String PRODUCT_ATTRIBUTE_NAME = "product";
    private static final String MESSAGE_ATTRIBUTE_NAME = "message";
    public static final String SUCCESS_MESSAGE = "Product Added to Cart Successfully!";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        computeSession(request);
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

        request.setCharacterEncoding("UTF-8");

        Long productId = getProductId(request);
        Product product = null;
        String message = null;
        String quantityString = (String) request.getParameter("quantity");

        try {
            message = executeOperations(request,product,productId,message,quantityString);
        }
        catch (EmptyFieldException | NotNumberException | LessEqualZeroAmountException | FractionalQuantityException | ProductNotEnoughException e) {
            message = e.getMessage();
        } catch (ProductNotFoundException e1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        setSessionAttributes(request,message,quantityString);
        response.sendRedirect(request.getRequestURI());
    }

    private void computeSession(HttpServletRequest request) {
        if(request.getSession().getAttribute("message") != null) {

            request.setAttribute("message",request.getSession().getAttribute("message"));
            request.setAttribute("quantity",request.getSession().getAttribute("quantity"));

            setSessionAttributes(request,null,null);
        }

    }

    private void setSessionAttributes(HttpServletRequest request,String ...attributes) {
        request.getSession().setAttribute(ProductDetailsPageServlet.MESSAGE_ATTRIBUTE_NAME,attributes[0]);
        request.getSession().setAttribute("quantity",attributes[1]);
    }

    private Integer parseAttribute(HttpServletRequest request,String attributeString) throws EmptyFieldException, NotNumberException, LessEqualZeroAmountException, FractionalQuantityException {

        if(attributeString == null || attributeString.isEmpty()) {
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

    private void setAttributes(HttpServletRequest request,Product product) {

        request.setAttribute("notEnough",ProductNotEnoughException.PRODUCT_NOT_ENOUGH_MESSAGE);
        request.setAttribute("notNumber",NotNumberException.NOT_NUMBER_MESSAGE);
        request.setAttribute("lessEqualZero",LessEqualZeroAmountException.LESS_EQUAL_ZERO_AMOUNT_MESSAGE);
        request.setAttribute("emptyField",EmptyFieldException.EMPTY_FIELD_MESSAGE);
        request.setAttribute("fractional",FractionalQuantityException.FRACTIONAL_QUANTITY_MESSAGE);
        request.setAttribute("success",ProductDetailsPageServlet.SUCCESS_MESSAGE);
        request.setAttribute(ProductDetailsPageServlet.PRODUCT_ATTRIBUTE_NAME,product);
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

    private String executeOperations(HttpServletRequest request,Product product,Long productId,String message,String quantityString) throws ProductNotFoundException, NotNumberException, FractionalQuantityException, LessEqualZeroAmountException, EmptyFieldException, ProductNotEnoughException {

        Integer quantity = parseAttribute(request,quantityString);
        product = productDao.getProduct(productId);
        cartService.add(cartService.getCart(request),product,quantity);
        message = ProductDetailsPageServlet.SUCCESS_MESSAGE;
        return message;
    }
}