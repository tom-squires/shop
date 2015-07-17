package com.tomsquires.shop.product;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class Product {
    public static final String CURRENCY_SYMBOL = "£";
    private final String productId;
    private final String productName;
    private final BigDecimal price;

    public Product(String productId, String productName, String price) {
        this.productId = productId.trim();
        this.productName = productName.trim();
        this.price = new BigDecimal(StringUtils.remove(price.trim(), CURRENCY_SYMBOL));
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + CURRENCY_SYMBOL + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!price.equals(product.price)) return false;
        if (!productId.equals(product.productId)) return false;
        if (!productName.equals(product.productName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + productName.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
