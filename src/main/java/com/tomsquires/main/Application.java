package com.tomsquires.main;

import com.tomsquires.shop.Basket;
import com.tomsquires.shop.Shop;
import com.tomsquires.shop.product.ProductLoader;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("************************");
        System.out.println("* Welcome to the  Shop *");
        System.out.println("************************");
        System.out.println("Enter \"Q\" to Quit");
        System.out.println("Enter \"add <ProductId>\" to add to basket");
        System.out.println("Enter \"remove <ProductId>\" to remove from basket");
        System.out.println("Enter \"list\" to show a list of products in the inventory");
        System.out.println("Enter \"total\" to show the total price of the basket");

        ProductLoader productLoader = new ProductLoader();
        Shop shop = new Shop(productLoader.loadProducts("src/main/resources/product-data.csv"), new Basket());

        while (true) {
            String inputValue = scanner.next();

            if (inputValue.startsWith("add")) {
                shop.addProductToBasket(scanner.next());

            } else if (inputValue.startsWith("remove")) {
                shop.removeProductFromBasket(scanner.next());

            } else if (inputValue.startsWith("list")) {
                shop.listProducts();

            } else if (inputValue.startsWith("total")) {
                shop.getTotal();

            } else if ("Q".equalsIgnoreCase(inputValue)) {
                System.out.println("Thanks for shopping with us!");
                System.exit(0);
            }
        }
    }
}
