package com.jamcito.inpost.productsrest.shopping;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QuoteTest {
    @Test
    public void testNoDiscountQuote() {
        Product product = new Product(10, 50);
        assertEquals(100, new Quote(product, 2).getQuoteCents().intValue());
    }

    @Test
    public void testCountBasedDiscountQuote() {
        Product product = new Product(10, 50, DiscountPolicy.COUNT_BASED, 3);
        assertEquals(94, new Quote(product, 2).getQuoteCents().intValue());
    }

    @Test
    public void testDiscountGreaterThanPriceQuote() {
        Product product = new Product(10, 50, DiscountPolicy.COUNT_BASED, 60);
        assertEquals(0, new Quote(product, 2).getQuoteCents().intValue());
    }

    @Test
    public void testPercentageDiscountQuote() {
        Product product = new Product(10, 50, DiscountPolicy.PERCENTAGE, 10);
        assertEquals(90, new Quote(product, 2).getQuoteCents().intValue());
    }

    @Test
    public void testPercentageDiscountRounding() {
        Product product = new Product(10, 10, DiscountPolicy.PERCENTAGE, 45);
        assertEquals(5, new Quote(product, 1).getQuoteCents().intValue());
    }

}
