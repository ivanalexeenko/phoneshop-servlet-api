package com.es.phoneshop.web;

<<<<<<< HEAD
import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SampleDataServletContextListener implements ServletContextListener {
    private ProductDao productDao;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        productDao = ArrayListProductDao.getInstance();
        ((ArrayListProductDao) productDao).pushDefaultProducts();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
=======
public class SampleDataServletContextListener {
>>>>>>> f8aad0c32716180d1a7d9b4e18259f30d7242195
}
