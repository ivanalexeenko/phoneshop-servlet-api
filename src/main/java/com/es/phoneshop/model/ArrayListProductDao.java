
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
        save(new Product("Apple iPhone X","New",BigDecimal.valueOf(7528.18),Currency.getInstance(Locale.CHINA),10));
        save(new Product("Huawei P20 Pro'","Brand New",BigDecimal.valueOf(691.35),Currency.getInstance(Locale.ITALY),1));
        save(new Product("Xiaomi Pocophone F1","Absolutely Fantastic!",BigDecimal.valueOf(460.00),Currency.getInstance(Locale.US),33));
        save(new Product("Sony XZ2 Premium","N/D",BigDecimal.valueOf(750.71),Currency.getInstance(Locale.UK),13));
        save(new Product("Motorola ZZ 11","Delicious",null,Currency.getInstance(Locale.CHINA),0));
        save(new Product("Honor 10","What is this?",BigDecimal.valueOf(3421.90),Currency.getInstance(Locale.CHINA),10000));
    }

    public void clearAll() {
        products.clear();
        currentId = 0L;
    }
}
