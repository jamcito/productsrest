package com.jamcito.inpost.productsrest.shopping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class ShoppingController {

    private final ProductModelAssembler productAssembler;
    private final ProductRepository productRepository;

    ShoppingController(ProductModelAssembler productAssembler, ProductRepository productRepository) {
        this.productAssembler = productAssembler;
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

    @PutMapping("/products/{id}")
    ResponseEntity<EntityModel<Product>> putProduct(@RequestBody Product product, @PathVariable UUID id) {
        Product updatedProduct = productRepository.findById(id)
                .map(oldproduct -> {
                    oldproduct.setCount(product.getCount());
                    oldproduct.setPrice(product.getPrice());
                    return productRepository.save(oldproduct);
                })
                .orElseGet(() -> {
                    product.setId(id);
                    return productRepository.save(product);
                });
        return ResponseEntity.ok(productAssembler.toModel(updatedProduct));
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable UUID id) {
        productRepository.deleteById(id);
    }
}