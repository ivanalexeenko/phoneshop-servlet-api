package com.es.phoneshop.web;

<<<<<<< HEAD
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("productDao", productDao);
        String path = request.getRequestURI();
        int index = path.lastIndexOf("/") + 1;
        path = path.substring(index);
        try {
            Long productId = Long.valueOf(path);
            Product product = productDao.getProduct(productId);
            request.setAttribute("product",product);
        }
        catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (ProductNotFoundException e1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }
=======
public class ProductDetailsPageServlet {
>>>>>>> f8aad0c32716180d1a7d9b4e18259f30d7242195
}
