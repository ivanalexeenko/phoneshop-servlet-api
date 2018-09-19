package com.es.phoneshop.web.servlet;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.classes.*;
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
import java.util.Optional;

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
    private final String COMMON_REMOVE_BUTTONS_STRING = "remove_";
    private final String REMOVE_ATTRIBUTE_NAME = "remove";
    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(ENCODING);
        request.getSession().setMaxInactiveInterval(CartPageServlet.MAX_SESSION_TIMEOUT_IN_SECONDS);
        request.setAttribute(CartPageServlet.CART_ATTRIBUTE_NAME, cartService.getCart(request));

        if(request.getSession().getAttribute(ERRORS_ATTRIBUTE_NAME) != null) {

            request.setAttribute(SUCCESS_ATTRIBUTE_NAME,request.getSession().getAttribute(SUCCESS_ATTRIBUTE_NAME));
            request.setAttribute(ERRORS_ATTRIBUTE_NAME,request.getSession().getAttribute(ERRORS_ATTRIBUTE_NAME));
            request.setAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME,request.getSession().getAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME));

        }

        Boolean remove = (Boolean) request.getSession().getAttribute(REMOVE_ATTRIBUTE_NAME);
        if(remove != null && !remove.equals(false)) {
            request.setAttribute(REMOVE_ATTRIBUTE_NAME,request.getSession().getAttribute(REMOVE_ATTRIBUTE_NAME));
        }

        request.getSession().setAttribute(REMOVE_ATTRIBUTE_NAME,false);
        request.getSession().setAttribute(SUCCESS_ATTRIBUTE_NAME,false);
        request.getSession().setAttribute(ERRORS_ATTRIBUTE_NAME, null);
        request.getSession().setAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME,null);

        request.setAttribute(ApplicationMessage.SUCCESS_HEAD.name(),ApplicationMessage.SUCCESS_HEAD.getCode());
        request.setAttribute(ApplicationMessage.CART_UPDATE_SUCCESS.name(),ApplicationMessage.CART_UPDATE_SUCCESS.getCode());
        request.setAttribute(ApplicationMessage.CART_ITEM_REMOVE_SUCCESS.name(),ApplicationMessage.CART_ITEM_REMOVE_SUCCESS.getCode());

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(ENCODING);

        boolean isRemove = false;

        Optional<CartItem> item = cartService.getCart(request).getCartItems().stream()
                .filter(cartItem -> request.getParameter(COMMON_REMOVE_BUTTONS_STRING + cartItem.getProduct().getId()) != null).findAny();
        if(item.isPresent()) {
            try {
                cartService.remove(cartService.getCart(request),item.get().getProduct().getId());
            } catch (CommonException ignored) {

            }
            isRemove = true;
        }
        else {
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
            ArrayList<String> quantityList = new ArrayList<>(Arrays.asList(newQuantities));

            request.getSession().setAttribute(SUCCESS_ATTRIBUTE_NAME,success);
            request.getSession().setAttribute(ERRORS_ATTRIBUTE_NAME, errorList);
            request.getSession().setAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME,quantityList);
        }

        request.getSession().setAttribute(REMOVE_ATTRIBUTE_NAME,isRemove);

        response.sendRedirect(request.getRequestURI());
    }


}
