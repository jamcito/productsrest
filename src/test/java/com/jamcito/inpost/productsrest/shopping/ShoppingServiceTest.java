package com.jamcito.inpost.productsrest.shopping;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import org.junit.runner.RunWith;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ShoppingService shoppingService;

    @Test
    public void testUpdateExistingProduct() {
        Product product = new Product(10, 100);
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(any())).then(returnsFirstArg());

        Product newProduct = shoppingService.updateProduct(product.getId(), 5, 1);
        assertEquals(new Product(5, 1), newProduct);
    }

    @Test
    public void testUpdateProductNewId() {
        UUID id = new UUID(1, 2);

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(productRepository.save(any())).then(returnsFirstArg());

        Product newProduct = shoppingService.updateProduct(id, 5, 1);
        assertEquals(id, newProduct.getId());
        assertEquals(Integer.valueOf(5), newProduct.getCount());
        assertEquals(Integer.valueOf(1), newProduct.getBasePriceCents());
    }

    @Test
    public void testSetDiscountExistingProduct() {
        Product product = new Product(10, 100, DiscountPolicy.COUNT_BASED, 5);
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(any())).then(returnsFirstArg());

        Product newProduct = shoppingService.setDiscount(product.getId(), DiscountPolicy.PERCENTAGE, 50);
        assertEquals(new Product(10, 100, DiscountPolicy.PERCENTAGE, 50), newProduct);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testSetDiscountNewId() {
        UUID id = new UUID(1, 2);

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(productRepository.save(any())).then(returnsFirstArg());

        shoppingService.setDiscount(id, DiscountPolicy.PERCENTAGE, 50);
    }
}
