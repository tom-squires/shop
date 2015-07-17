package com.tomsquires.shop;

import com.tomsquires.shop.product.Product;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ShopTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void listProductsListsAllProducts() {
        // Given
        final Map<String, Product> products = new HashMap<String, Product>() {{
            put("1", new Product("1", "Trousers", "49.99"));
            put("2", new Product("2", "Jumper", "29.99"));
            put("3", new Product("3", "Socks", "4.99"));
        }};
        final Shop shop = new Shop(products, mock(Basket.class));

        // When
        shop.listProducts();

        // Then
        assertTrue("should have listed Trousers product",
                outContent.toString().contains("Product{productId='1', productName='Trousers', price='£49.99'}"));
        assertTrue("should have listed Jumper product",
                outContent.toString().contains("Product{productId='2', productName='Jumper', price='£29.99'}"));
        assertTrue("should have listed Socks product",
                outContent.toString().contains("Product{productId='3', productName='Socks', price='£4.99'}"));
    }

    @Test
    public void ifNoProductsThenListAllProductsListsNothing() {
        // Given
        final Shop shop = new Shop(Collections.emptyMap(), mock(Basket.class));

        // When
        shop.listProducts();

        // Then
        assertTrue("output should be empty", StringUtils.isEmpty(outContent.toString()));
    }

    @Test
    public void addingValidProductAddsToBasket() {
        // Given
        final Map<String, Product> products = new HashMap<String, Product>() {{
            put("1", new Product("1", "Trousers", "49.99"));
        }};
        final Basket mockBasket = mock(Basket.class);
        final Shop shop = new Shop(products, mockBasket);

        // When
        when(mockBasket.contains("1")).thenReturn(false);
        shop.addProductToBasket("1");

        // Then
        verify(mockBasket, times(1)).add(new Product("1", "Trousers", "49.99"));
        assertTrue("should be able to add product to basket",
                "Successfully added product with ID '1' to basket.\n".equals(outContent.toString()));
    }

    @Test
    public void addingInvalidProductDoesNotAddToBasket() {
        // Given
        final Map<String, Product> products = new HashMap<String, Product>() {{
            put("1", new Product("1", "Trousers", "49.99"));
        }};
        final Basket mockBasket = mock(Basket.class);
        final Shop shop = new Shop(products, mockBasket);

        // When
        shop.addProductToBasket("2");

        // Then
        verify(mockBasket, never()).add(any(Product.class));
        assertTrue("should not be able to add product to basket",
                "Unable to find productId '2' in catalogue.\n".equals(outContent.toString()));
    }

    @Test
    public void cannotAddProductIfAlreadyInBasket() {
        // Given
        final Map<String, Product> products = new HashMap<String, Product>() {{
            put("1", new Product("1", "Trousers", "49.99"));
        }};
        final Basket mockBasket = mock(Basket.class);
        final Shop shop = new Shop(products, mockBasket);

        // When
        when(mockBasket.contains("1")).thenReturn(true);
        shop.addProductToBasket("1");

        // Then
        verify(mockBasket, never()).add(any(Product.class));
        assertTrue("should not be able to add product already in basket",
                "Basket already contains product with ID '1'\n".equals(outContent.toString()));
    }

    @Test
    public void removingProductPresentInBasketActuallyRemovesFromBasket() {
        // Given
        final Basket mockBasket = mock(Basket.class);
        final Shop shop = new Shop(Collections.emptyMap(), mockBasket);

        // When
        when(mockBasket.contains("1")).thenReturn(true);
        shop.removeProductFromBasket("1");

        // Then
        verify(mockBasket, times(1)).remove("1");
        assertTrue("should be able to remove product from basket",
                "Successfully removed product with ID '1' from basket.\n".equals(outContent.toString()));
    }

    @Test
    public void removingProductNotInBasketDisplaysErrorText() {
        // Given
        final Basket mockBasket = mock(Basket.class);
        final Shop shop = new Shop(Collections.emptyMap(), mockBasket);

        // When
        when(mockBasket.contains("1")).thenReturn(false);
        shop.removeProductFromBasket("1");

        // Then
        verify(mockBasket, never()).remove(anyString());
        assertTrue("should not be able to remove product if not in basket",
                "Unable to find productId '1' in basket.\n".equals(outContent.toString()));
    }

    @Test
    public void getTotalReturnsCorrectTotal() {
        // Given
        final Basket mockBasket = mock(Basket.class);
        final Shop shop = new Shop(Collections.emptyMap(), mockBasket);

        // When
        when(mockBasket.getTotal()).thenReturn(new BigDecimal("36.00"));
        shop.getTotal();

        // Then
        assertTrue("total cost not as expected", "Total cost of goods in basket: £36.00\n".equals(outContent.toString()));
    }
}