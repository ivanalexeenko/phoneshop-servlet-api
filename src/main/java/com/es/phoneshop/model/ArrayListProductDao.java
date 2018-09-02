package com.es.phoneshop.model;

import com.es.phoneshop.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static Long currentId = 0L;
    private List<Product> products;

    private ArrayListProductDao() {
        this.products = new ArrayList<Product>();
    }

    private static class ArrayListProductDaoHelper {
        private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();
    }

    public static ArrayListProductDao getInstance() {
        return ArrayListProductDaoHelper.INSTANCE;
    }

    public List<Product> findProducts() {
        return products.stream().filter(product -> product.getPrice() != null && product.getStock() != null && product.getStock() > 0 ).collect(Collectors.toList());
    }

    public Product getProduct(Long id) throws ProductNotFoundException {
        for(Product currentProduct : products) {
            if(currentProduct.getId().compareTo(id) == 0) {
                return currentProduct;
            }
        }
        throw new ProductNotFoundException(ProductNotFoundException.productNotFoundMessage + "(ID:" + id + ")");
    }

    public void save(Product product) {
        ArrayListProductDao.currentId++;
        product.setId(ArrayListProductDao.currentId);
        products.add(product);
    }

    public void remove(Long id) throws ProductNotFoundException {
        for(Product currentProduct : products) {
            if(currentProduct.getId().compareTo(id) == 0) {
                products.remove(currentProduct);
                return;
            }
        }
        throw new ProductNotFoundException(ProductNotFoundException.productNotFoundMessage + "(ID:" + id + ")");
    }
    public void pushDefaultProducts() {
        save(new Product("u72u1","New",BigDecimal.valueOf(900),Currency.getInstance(Locale.CHINA),10));
        save(new Product("PPoPP2'","Brand New",BigDecimal.valueOf(777),Currency.getInstance(Locale.ITALY),1));
        save(new Product("lkasfa21","New",BigDecimal.valueOf(900),Currency.getInstance(Locale.GERMANY),0));
        save(new Product("Pa1p13","NA",BigDecimal.valueOf(0),Currency.getInstance(Locale.KOREA),13));
        save(new Product("EX00pe11RT","Delicious",null,Currency.getInstance(Locale.JAPAN),94));
        save(new Product("OOOOOO000","What is this",BigDecimal.valueOf(-213),Currency.getInstance(Locale.CHINA),1000));
    }
    public void clearAll() {
        products.clear();
        currentId = 0L;
    }
}
