package com.tomsquires.shop.product;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ProductLoader {
    public Map<String, Product> loadProducts(String productsFile) {
        try {
            final Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(new FileReader(productsFile));
            final Map<String, Product> products = new HashMap<>();
            for (CSVRecord record : records) {
                final String productId = record.get("#productId");
                products.put(productId, new Product(productId, record.get("#productName"), record.get("#price")));
            }
            return products;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
