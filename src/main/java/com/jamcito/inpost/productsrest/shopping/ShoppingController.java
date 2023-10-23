package com.jamcito.inpost.productsrest.shopping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jamcito.inpost.productsrest.shopping.model.discount.CountBasedDiscountDefinition;
import com.jamcito.inpost.productsrest.shopping.model.discount.PercentageDiscountDefinition;
import com.jamcito.inpost.productsrest.shopping.model.product.DiscountPolicy;
import com.jamcito.inpost.productsrest.shopping.model.product.Product;
import com.jamcito.inpost.productsrest.shopping.model.product.ProductInputDTO;
import com.jamcito.inpost.productsrest.shopping.model.quote.Quote;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@Validated
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
    ResponseEntity<EntityModel<Product>> postProduct(@Valid @RequestBody ProductInputDTO productInputDTO) {
        Product newProduct = shoppingService.createProduct(productInputDTO.getCount(),
                productInputDTO.getBasePriceCents());
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
            @Min(1) @RequestParam(value = "count", defaultValue = "1") Integer count) {
        Quote quote = shoppingService.getQuote(id, count);

        return ResponseEntity.ok(quoteAssembler.toModel(quote));
    }

    @PutMapping("/products/{id}")
    ResponseEntity<EntityModel<Product>> putProduct(@Valid @RequestBody ProductInputDTO productInputDTO,
            @PathVariable UUID id) {
        Product product = shoppingService.updateProduct(id, productInputDTO.getCount(),
                productInputDTO.getBasePriceCents());

        return ResponseEntity.ok(productAssembler.toModel(product));
    }

    @PutMapping("/products/{id}/count-discount")
    ResponseEntity<EntityModel<Product>> putCountDiscount(@Valid @RequestBody CountBasedDiscountDefinition discount,
            @PathVariable UUID id) {
        Product product = shoppingService.setDiscount(id, DiscountPolicy.COUNT_BASED, discount.getDiscountAmount());

        return ResponseEntity.ok(productAssembler.toModel(product));
    }

    @PutMapping("/products/{id}/percentage-discount")
    ResponseEntity<EntityModel<Product>> putPercentageDiscount(
            @Valid @RequestBody PercentageDiscountDefinition discount, @PathVariable UUID id) {
        Product product = shoppingService.setDiscount(id, DiscountPolicy.PERCENTAGE,
                discount.getDiscountPercentage());

        return ResponseEntity.ok(productAssembler.toModel(product));
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable UUID id) {
        shoppingService.deleteProduct(id);
    }
}