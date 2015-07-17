package com.tomsquires.shop;

import com.tomsquires.shop.product.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    private final Map<String, Product> products;

    public Basket() {
        this.products = new HashMap<>();
    }

    public void add(Product product) {
        products.put(product.getProductId(), product);
    }

    public boolean contains(String productId) {
        return products.containsKey(productId);
    }

    public void remove(String productId) {
        products.remove(productId);
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0.00");
        for (String productId : products.keySet()) {
            total = total.add(products.get(productId).getPrice());
        }
        return total;
    }
}
