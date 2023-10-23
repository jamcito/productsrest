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
    private final ShoppingService shoppingService;

    ShoppingController(ProductModelAssembler productAssembler, QuoteRepresentationModelAssembler quoteAssembler,
            ShoppingService shoppingService) {
        this.productAssembler = productAssembler;
        this.quoteAssembler = quoteAssembler;
        this.shoppingService = shoppingService;
    }

    @GetMapping("/products")
    ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts() {
        List<EntityModel<Product>> products = shoppingService.findAll().stream()
                .map(productAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(CollectionModel.of(products,
                        linkTo(methodOn(ShoppingController.class).getAllProducts()).withSelfRel()));
    }

    @PostMapping("/products")
    ResponseEntity<EntityModel<Product>> postProduct(@RequestBody Product product) {
        Product newProduct = shoppingService.createProduct(product.getCount(),
                product.getBasePriceCents());
        EntityModel<Product> entityModel = productAssembler.toModel(newProduct);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/products/{id}")
    ResponseEntity<EntityModel<Product>> getProduct(@PathVariable UUID id) {
        Product product = shoppingService.getProduct(id);

        return ResponseEntity.ok(productAssembler.toModel(product));
    }

    @GetMapping("/products/{id}/quote")
    ResponseEntity<RepresentationModel<Quote>> getQuote(@PathVariable UUID id,
            @RequestParam(value = "count", defaultValue = "1") Integer count) {
        Quote quote = shoppingService.getQuote(id, count);

        return ResponseEntity.ok(quoteAssembler.toModel(quote));
    }

    @PutMapping("/products/{id}")
    ResponseEntity<EntityModel<Product>> putProduct(@RequestBody Product product, @PathVariable UUID id) {
        Product newProduct = shoppingService.updateProduct(id, product.getCount(), product.getBasePriceCents());

        return ResponseEntity.ok(productAssembler.toModel(newProduct));
    }

    @PutMapping("/products/{id}/count-discount")
    ResponseEntity<EntityModel<Product>> putCountDiscount(@RequestBody DiscountDefinition discount,
            @PathVariable UUID id) {
        Product product = shoppingService.setDiscount(id, DiscountPolicy.COUNT_BASED, discount.getDiscountFactor());

        return ResponseEntity.ok(productAssembler.toModel(product));
    }

    @PutMapping("/products/{id}/percentage-discount")
    ResponseEntity<EntityModel<Product>> putPercentageDiscount(@RequestBody DiscountDefinition discount,
            @PathVariable UUID id) {
        Product product = shoppingService.setDiscount(id, DiscountPolicy.PERCENTAGE,
                discount.getDiscountFactor());

        return ResponseEntity.ok(productAssembler.toModel(product));
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable UUID id) {
        shoppingService.deleteProduct(id);
    }
}