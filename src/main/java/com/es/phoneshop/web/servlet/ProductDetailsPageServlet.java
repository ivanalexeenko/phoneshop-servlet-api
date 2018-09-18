package com.es.phoneshop.web.servlet;
import com.es.phoneshop.exception.*;
import com.es.phoneshop.model.classes.ApplicationMessage;
import com.es.phoneshop.model.classes.ArrayListProductDao;
import com.es.phoneshop.model.classes.CartService;
import com.es.phoneshop.model.classes.Product;
import com.es.phoneshop.model.interfaces.CartServiceInterface;
import com.es.phoneshop.model.interfaces.ProductDao;
import com.es.phoneshop.parser.AttributeParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao = ArrayListProductDao.getInstance();
    private CartServiceInterface cartService = CartService.getInstance();
    private final String PRODUCT_ATTRIBUTE_NAME = "product";
    private final String MESSAGE_CODE_ATTRIBUTE_NAME = "messageCode";
    private final String QUANTITY_ATTRIBUTE_NAME = "quantity";
    private final String ENCODING = "UTF-8";
    private AttributeParser parser = new AttributeParser();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding(ENCODING);
        handleSession(request);
        Product product = null;

        try {
            product = productDao.getProduct(getProductId(request));
            setAttributes(request,product);
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }
        catch (CommonException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        setAttributes(request,product);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding(ENCODING);

        Long productId = getProductId(request);
        Product product = null;
        Integer messageCode = ApplicationMessage.DEFAULT_CODE.getCode();
        String quantityString = (String) request.getParameter(QUANTITY_ATTRIBUTE_NAME);
        try {
            messageCode = executeOperations(request,product,productId,messageCode,quantityString);
        }
        catch (CommonException e) {
            messageCode = e.getCode();
        }

        setSessionAttributes(request,messageCode,quantityString);
        response.sendRedirect(request.getRequestURI());
    }

    private void handleSession(HttpServletRequest request) {
        if(request.getSession().getAttribute(QUANTITY_ATTRIBUTE_NAME) != null) {

            request.setAttribute(MESSAGE_CODE_ATTRIBUTE_NAME,request.getSession().getAttribute(MESSAGE_CODE_ATTRIBUTE_NAME));
            request.setAttribute(QUANTITY_ATTRIBUTE_NAME,request.getSession().getAttribute(QUANTITY_ATTRIBUTE_NAME));

        }
        setSessionAttributes(request,ApplicationMessage.DEFAULT_CODE.getCode(),null);

    }

    private void setSessionAttributes(HttpServletRequest request,Integer code,String quantity) {
        request.getSession().setAttribute(MESSAGE_CODE_ATTRIBUTE_NAME,code);
        request.getSession().setAttribute(QUANTITY_ATTRIBUTE_NAME,quantity);
    }

    private void setAttributes(HttpServletRequest request,Product product) {

        request.setAttribute(PRODUCT_ATTRIBUTE_NAME,product);

        request.setAttribute(ApplicationMessage.SUCCESS_HEAD.name(),ApplicationMessage.SUCCESS_HEAD.getCode());
        request.setAttribute(ApplicationMessage.ERROR_HEAD.name(),ApplicationMessage.ERROR_HEAD.getCode());
        request.setAttribute(ApplicationMessage.SUCCESS.name(),ApplicationMessage.SUCCESS.getCode());
        request.setAttribute(ApplicationMessage.DEFAULT_CODE.name(),ApplicationMessage.DEFAULT_CODE.getCode());

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

    private Integer executeOperations(HttpServletRequest request,Product product,Long productId,Integer message,String quantityString) throws CommonException {

        Integer quantity = parser.parseAttribute(request,quantityString);
        product = productDao.getProduct(productId);
        cartService.add(cartService.getCart(request),product,quantity);
        message = ApplicationMessage.SUCCESS.getCode();
        return message;
    }
}