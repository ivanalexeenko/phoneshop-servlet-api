package com.es.phoneshop.web;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private static final String PRODUCT_DAO_ATTRIBUTE_NAME = "productDao";
    private static final String PRODUCT_ATTRIBUTE_NAME = "product";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ProductDetailsPageServlet.PRODUCT_DAO_ATTRIBUTE_NAME, productDao);
        try {
            String path = request.getPathInfo();
            if(path == null || path.compareTo("/") == 0) {
                throw new StringIndexOutOfBoundsException();
            }
            int index = path.indexOf("/") + 1;
            path = path.substring(index);
            Long productId = Long.valueOf(path);
            Product product = productDao.getProduct(productId);
            request.setAttribute(ProductDetailsPageServlet.PRODUCT_ATTRIBUTE_NAME, product);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ProductNotFoundException e1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }
}