package com.es.phoneshop.web;
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
}