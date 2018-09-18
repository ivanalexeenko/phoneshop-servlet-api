package com.es.phoneshop.web.servlet;

import com.es.phoneshop.exception.CommonException;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartPageServlet extends HttpServlet {

    private CartServiceInterface cartService = CartService.getInstance();
    public static final String CART_ATTRIBUTE_NAME = "cart";
    private static final int MAX_SESSION_TIMEOUT_IN_SECONDS = 20 * 60;
    private final String PRODUCT_ID_ATTRIBUTE_NAME = "productId";
    private final String NEW_QUANTITY_ATTRIBUTE_NAME = "newQuantity";
    private final String NEW_QUANTITIES_ATTRIBUTE_NAME = "newQuantities";
    private final String ERRORS_ATTRIBUTE_NAME = "errors";
    private final String SUCCESS_ATTRIBUTE_NAME = "success";
    private AttributeParser parser = new AttributeParser();
    private final String ENCODING = "UTF-8";
    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(ENCODING);
        request.getSession().setMaxInactiveInterval(CartPageServlet.MAX_SESSION_TIMEOUT_IN_SECONDS);
        request.setAttribute(CartPageServlet.CART_ATTRIBUTE_NAME, cartService.getCart(request));

        if(request.getSession().getAttribute(ERRORS_ATTRIBUTE_NAME) != null) {

            request.setAttribute(ERRORS_ATTRIBUTE_NAME,request.getSession().getAttribute(ERRORS_ATTRIBUTE_NAME));
            request.setAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME,request.getSession().getAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME));

        }
        request.getSession().setAttribute(SUCCESS_ATTRIBUTE_NAME,null);
        request.getSession().setAttribute(ERRORS_ATTRIBUTE_NAME, null);
        request.getSession().setAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME,null);


        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(ENCODING);
        Boolean success = true;
        String[] productIds = request.getParameterValues(PRODUCT_ID_ATTRIBUTE_NAME);
        String[] newQuantities = request.getParameterValues(NEW_QUANTITY_ATTRIBUTE_NAME);
        Integer[] errors = new Integer[productIds.length];
        for(int i = 0;i < productIds.length;i++) {
            try {
                Product product = productDao.getProduct(Long.valueOf(productIds[i]));
                Integer quantity = null;
                quantity = parser.parseAttribute(request,newQuantities[i]);
                cartService.update(cartService.getCart(request),product,quantity);
                response.sendRedirect(request.getRequestURI());
                return;
            }
            catch (NumberFormatException e) {
                errors[i] = ApplicationMessage.NOT_NUMBER.getCode();
                success = false;
            }
            catch (CommonException e) {
                errors[i] = e.getCode();
                success = false;
            }
        }
        ArrayList<Integer> errorList = new ArrayList<>(Arrays.asList(errors));
        ArrayList<String> quantityList = new ArrayList<String>(Arrays.asList(newQuantities));

        request.getSession().setAttribute(SUCCESS_ATTRIBUTE_NAME,success);
        request.getSession().setAttribute(ERRORS_ATTRIBUTE_NAME, errorList);
        request.getSession().setAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME,quantityList);

        response.sendRedirect(request.getRequestURI());
    }


}
