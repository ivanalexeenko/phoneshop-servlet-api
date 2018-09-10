package com.es.phoneshop.web;

import com.es.phoneshop.additional.ArrayListVisitedPages;
import com.es.phoneshop.additional.VisitedPagesInterface;
import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ProductListServlet extends HttpServlet {
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private static final String PRODUCTS_ATTRIBUTE_NAME = "products";
    private VisitedPagesInterface visitedPages = ArrayListVisitedPages.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        visitedPages.saveAddress(request.getRequestURI());
        request.setAttribute(ProductListServlet.PRODUCTS_ATTRIBUTE_NAME, productDao.findProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
