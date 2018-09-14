package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListServlet extends HttpServlet {

    private ProductDao productDao = ArrayListProductDao.getInstance();
    private static final String PRODUCTS_ATTRIBUTE_NAME = "products";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute(ProductListServlet.PRODUCTS_ATTRIBUTE_NAME, productDao.findProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
