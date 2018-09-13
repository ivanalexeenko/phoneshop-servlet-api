package com.es.phoneshop.model;

import com.es.phoneshop.exception.ProductNotFoundException;

import java.util.List;

public interface ProductDao {

    public List<Product> findProducts();

    public Product getProduct(Long id)  throws ProductNotFoundException;

    public void save(Product product);

    public void remove(Long id) throws ProductNotFoundException;

    public void clearAll();
}
