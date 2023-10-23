package com.jamcito.inpost.productsrest.shopping;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jamcito.inpost.productsrest.shopping.exception.InsufficientProductCountException;
import com.jamcito.inpost.productsrest.shopping.exception.ProductNotFoundException;
import com.jamcito.inpost.productsrest.shopping.model.product.DiscountPolicy;
import com.jamcito.inpost.productsrest.shopping.model.product.Product;
import com.jamcito.inpost.productsrest.shopping.model.quote.Quote;

@Service
public class ShoppingService {

    private final ProductRepository productRepository;

    public ShoppingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product createProduct(Integer count, Integer basePriceCents) {
        Product newProduct = new Product(count, basePriceCents);
        return productRepository.save(newProduct);
    }

    public Product updateProduct(UUID id, Integer count, Integer basePriceCents) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setCount(count);
                    product.setBasePriceCents(basePriceCents);
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    Product newProduct = new Product(count, basePriceCents);
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
    }

    public Product setDiscount(UUID id, DiscountPolicy policy, Integer discountFactor) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setDiscountPolicy(policy);
                    product.setDiscountFactor(discountFactor);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product getProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Quote getQuote(UUID id, Integer count) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        if (count > product.getCount())
            throw new InsufficientProductCountException(id);
        return new Quote(product, count);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}