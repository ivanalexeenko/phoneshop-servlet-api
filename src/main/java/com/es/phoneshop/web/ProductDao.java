package com.es.phoneshop.web;

import java.util.List;

public interface ProductDao {
    public List<Product> findProducts();
    public Product getProduct(Long id);
    public void save(Product product);
    public void remove(Long id);
}
