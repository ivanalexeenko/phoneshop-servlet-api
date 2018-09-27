package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.es.phoneshop.model.helping.Constants.*;

public class ProductListServlet extends HttpServlet {

    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute(PRODUCTS_ATTRIBUTE_NAME, productDao.findProducts());
        Boolean attribute = (Boolean) request.getSession().getAttribute(IS_SEARCHING_ATTRIBUTE_NAME);
        if(attribute != null && !attribute.equals(Boolean.FALSE)) {
            request.setAttribute(IS_SEARCHING_ATTRIBUTE_NAME,attribute);
        }

        request.getSession().setAttribute(IS_SEARCHING_ATTRIBUTE_NAME, Boolean.FALSE);
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter(SEARCH_FIELD_PARAM_NAME);
        if(search == null) {
            search = "";
        }
        request.getSession().setAttribute(SEARCH_ATTRIBUTE_NAME,search);

        request.getSession().setAttribute(SEARCH_LIST_ATTRIBUTE_NAME,searchProducts(search));
        request.getSession().setAttribute(IS_SEARCHING_ATTRIBUTE_NAME,Boolean.TRUE);
        response.sendRedirect(request.getRequestURI());
    }
    private List<Product> searchProducts(String search) {
        List<Product> products = productDao.findProducts();
        List<Product> tempList = new ArrayList<>();
        String find = search.toLowerCase();
        tempList = products.stream().filter(product -> product.getCode().toLowerCase().contains(find) || product.getDescription().contains(find)).collect(Collectors.toList());
        tempList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if(o1.getCode().compareTo(o2.getCode()) == 0) {
                    return o1.getDescription().compareTo(o2.getDescription());
                }
                return o1.getCode().compareTo(o2.getCode());
            }
        });
        return tempList;
    }
}
