package com.tomsquires.shop;

import com.tomsquires.shop.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BasketTest {
    private Basket basket;

    @Before
    public void setUp() {
        this.basket = new Basket();
    }

    @Test
    public void addingToBasketThenCallingContainsShouldReturnTrue() {
        // When
        basket.add(new Product("1", "Trousers", "£29.99"));

        // Then
        assertTrue("basket should contain product", basket.contains("1"));
    }

    @Test
    public void callingContainsOnEmptyBasketShouldReturnFalse() {
        assertFalse("basket should not contain product", basket.contains("1"));
    }

    @Test
    public void addAndRemoveShouldYieldEmptyBasket() {
        // When
        basket.add(new Product("1", "Trousers", "£29.99"));

        // Then
        assertTrue("basket should contain product", basket.contains("1"));
        // And
        basket.remove("1");
        assertFalse("basket should not contain product", basket.contains("1"));
    }

    @Test
    public void testTotalForOneProduct() {
        // When
        basket.add(new Product("1", "Trousers", "£29.99"));

        // Then
        assertEquals("total not as expected", new BigDecimal("29.99"), basket.getTotal());
    }

    @Test
    public void testTotalForMultipleProducts() {
        // When
        basket.add(new Product("1", "Trousers", "£39.99"));
        basket.add(new Product("2", "Jumper", "£10.00"));
        basket.add(new Product("3", "Socks", "5.00"));

        // Then
        assertEquals("total not as expected", new BigDecimal("54.99"), basket.getTotal());
    }

    @Test
    public void totalForNoProductsShouldBeZero() {
        // Then
        assertEquals("total not as expected", new BigDecimal("0.00"), basket.getTotal());
    }
}