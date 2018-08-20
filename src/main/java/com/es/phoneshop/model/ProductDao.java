package com.es.phoneshop.model;

import java.util.List;

public interface ProductDao {
    public List<Product> findProducts();
    public Product getProduct(Long id);
    public void save(Product product);
    public void remove(Long id);
}
