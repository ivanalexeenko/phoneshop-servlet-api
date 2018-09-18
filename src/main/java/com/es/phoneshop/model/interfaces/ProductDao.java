package com.es.phoneshop.model.interfaces;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.classes.Product;

import java.util.List;

public interface ProductDao {

    public List<Product> findProducts();

    public Product getProduct(Long id) throws CommonException;

    public void save(Product product);

    public void remove(Long id) throws CommonException;

    public void clearAll();
}
