package com.es.phoneshop.web;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class Product {
    private Long id;
    private String code;
    private String description;
    private BigDecimal price;
    private Currency currency;
    private Integer stock;

    public Product() {
        this.code = "";
        this.description = "";
        this.price = new BigDecimal(0);
        this.currency = Currency.getInstance(Locale.getDefault());
        this.stock = 0;
    }

    public Product(String code, String description, BigDecimal price, Currency currency, Integer stock) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Integer getStock() {
        return stock;
    }
}
