package com.jamcito.inpost.productsrest.shopping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ShoppingController {
    private final ProductRepository productRepository;

    ShoppingController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    Product postProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/products/{id}")
    Product getProduct(@PathVariable UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/products/{id}")
    Product putProduct(@RequestBody Product product, @PathVariable UUID id) {
        return productRepository.findById(id)
                .map(oldproduct -> {
                    oldproduct.setCount(product.getCount());
                    oldproduct.setPrice(product.getPrice());
                    return productRepository.save(oldproduct);
                })
                .orElseGet(() -> {
                    product.setId(id);
                    return productRepository.save(product);
                });
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable UUID id) {
        productRepository.deleteById(id);
    }
}