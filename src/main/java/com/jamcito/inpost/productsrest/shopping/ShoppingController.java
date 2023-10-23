package com.jamcito.inpost.productsrest.shopping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class ShoppingController {

    private final ProductModelAssembler productAssembler;
    private final QuoteRepresentationModelAssembler quoteAssembler;
    private final ProductRepository productRepository;

    ShoppingController(ProductModelAssembler productAssembler, QuoteRepresentationModelAssembler quoteAssembler,
            ProductRepository productRepository) {
        this.productAssembler = productAssembler;
        this.quoteAssembler = quoteAssembler;
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts() {
        List<EntityModel<Product>> products = productRepository.findAll().stream()
                .map(productAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(CollectionModel.of(products,
                        linkTo(methodOn(ShoppingController.class).getAllProducts()).withSelfRel()));
    }

    @PostMapping("/products")
    ResponseEntity<EntityModel<Product>> postProduct(@RequestBody Product product) {
        EntityModel<Product> entityModel = productAssembler.toModel(productRepository.save(product));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/products/{id}")
    ResponseEntity<EntityModel<Product>> getProduct(@PathVariable UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ResponseEntity.ok(productAssembler.toModel(product));
    }

    @GetMapping("/products/{id}/quote")
    ResponseEntity<RepresentationModel<Quote>> getQuote(@PathVariable UUID id,
            @RequestParam(value = "count", defaultValue = "1") Integer count) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        if (count > product.getCount())
            throw new InsufficientProductCountException(id);

        return ResponseEntity.ok(quoteAssembler.toModel(new Quote(product, count)));
    }

    @PutMapping("/products/{id}")
    ResponseEntity<EntityModel<Product>> putProduct(@RequestBody Product product, @PathVariable UUID id) {
        Product updatedProduct = productRepository.findById(id)
                .map(oldproduct -> {
                    oldproduct.setCount(product.getCount());
                    oldproduct.setBasePriceCents(product.getBasePriceCents());
                    return productRepository.save(oldproduct);
                })
                .orElseGet(() -> {
                    product.setId(id);
                    return productRepository.save(product);
                });
        return ResponseEntity.ok(productAssembler.toModel(updatedProduct));
    }


    @PutMapping("/products/{id}/count-discount")
    ResponseEntity<EntityModel<Product>> putCountDiscount(@RequestBody DiscountDefinition discount, @PathVariable UUID id) {
        Product updatedProduct = productRepository.findById(id)
                    .map(product -> {
                        product.setDiscountPolicy(DiscountPolicy.COUNT_BASED);
                        product.setDiscountFactor(discount.getDiscountFactor());
                        return productRepository.save(product);
                    })
                    .orElseThrow(() -> new ProductNotFoundException(id));

        return ResponseEntity.ok(productAssembler.toModel(updatedProduct));
    }

    @PutMapping("/products/{id}/percentage-discount")
    ResponseEntity<EntityModel<Product>> putPercentageDiscount(@RequestBody DiscountDefinition discount, @PathVariable UUID id) {
        Product updatedProduct = productRepository.findById(id)
                    .map(product -> {
                        product.setDiscountPolicy(DiscountPolicy.PERCENTAGE);
                        product.setDiscountFactor(discount.getDiscountFactor());
                        return productRepository.save(product);
                    })
                    .orElseThrow(() -> new ProductNotFoundException(id));

        return ResponseEntity.ok(productAssembler.toModel(updatedProduct));
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable UUID id) {
        productRepository.deleteById(id);
    }
}