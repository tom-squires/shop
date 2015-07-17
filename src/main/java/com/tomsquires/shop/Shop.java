package com.tomsquires.shop;

import com.tomsquires.shop.product.Product;

import java.util.Map;

public class Shop {
    private final Map<String, Product> products;
    private final Basket basket;

    public Shop(Map<String, Product> products, Basket basket) {
        this.products = products;
        this.basket = basket;
    }

    public void listProducts() {
        for (String productId : products.keySet()) {
            System.out.println(products.get(productId));
        }
    }

    public void addProductToBasket(String productId) {
        if (!products.containsKey(productId)) {
            System.out.println("Unable to find productId '" + productId + "' in catalogue.");
        } else if (basket.contains(productId)) {
            System.out.println("Basket already contains product with ID '" + productId + "'");
        } else {
            basket.add(products.get(productId));
            System.out.println("Successfully added product with ID '" + productId + "' to basket.");
        }
    }

    public void removeProductFromBasket(String productId) {
        if (!basket.contains(productId)) {
            System.out.println("Unable to find productId '" + productId + "' in basket.");
        } else {
            basket.remove(productId);
            System.out.println("Successfully removed product with ID '" + productId + "' from basket.");
        }
    }

    public void getTotal() {
        System.out.println("Total cost of goods in basket: " + Product.CURRENCY_SYMBOL + basket.getTotal());
    }

}
