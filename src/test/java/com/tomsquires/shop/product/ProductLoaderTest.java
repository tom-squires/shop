package com.tomsquires.shop.product;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductLoaderTest {
    private ProductLoader productLoader;

    @Before
    public void setUp() {
        this.productLoader = new ProductLoader();
    }

    @Test
    public void canLoadOneProduct() {
        // When
        final Map<String, Product> products = productLoader.loadProducts("src/test/resources/one-product.csv");

        // Then
        assertEquals("should only be 1 product", 1, products.size());
        assertEquals("product not as expected", new Product("1", "Short Sleeve Jumper", "9.99"), products.get("1"));
    }

    @Test
    public void canLoadMultipleProducts() {
        // When
        final Map<String, Product> products = productLoader.loadProducts("src/test/resources/multiple-products.csv");

        // Then
        assertEquals("should only be 3 products", 3, products.size());
        assertEquals("product not as expected", new Product("1", "Short Sleeve Jumper", "9.99"), products.get("1"));
        assertEquals("product not as expected", new Product("2", "Shoulder Bag", "9.99"), products.get("2"));
        assertEquals("product not as expected", new Product("3", "Skinny Jeans", "45.00"), products.get("3"));
    }

    @Test
    public void canLoadMultipleProductsAndTrimWhitespaces() {
        // When
        final Map<String, Product> products = productLoader.loadProducts("src/test/resources/multiple-products-whitespaces.csv");

        // Then
        assertEquals("should only be 3 products", 3, products.size());
        assertEquals("product not as expected", new Product("1", "Short Sleeve Jumper", "9.99"), products.get("1"));
        assertEquals("product not as expected", new Product("6", "Wool Socks", "20.50"), products.get("6"));
        assertEquals("product not as expected", new Product("7", "Piqué Polo shirt", "50.55"), products.get("7"));
    }

    @Test
    public void loadsNoProductsWhenProductFileEmpty() {
        // When
        final Map<String, Product> products = productLoader.loadProducts("src/test/resources/no-products.csv");

        // Then
        assertTrue("should be no products", products.isEmpty());
    }

    @Test
    public void loadsNoProductsWhenProductFileContainsHeadersButNoRows() {
        // When
        final Map<String, Product> products = productLoader.loadProducts("src/test/resources/no-products2.csv");

        // Then
        assertTrue("should be no products", products.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void throwsIllegalStateExceptionWhenProductDataFileNotFound() {
        productLoader.loadProducts("doesnt-exist.csv");
    }

    @Test(expected = IllegalStateException.class)
    public void throwsIllegalStateExceptionWhenColumnHeadersInvalid() {
        productLoader.loadProducts("src/test/resources/invalid-headers.csv");
    }
}