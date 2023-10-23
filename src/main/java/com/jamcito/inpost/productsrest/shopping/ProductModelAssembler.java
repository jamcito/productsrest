package com.jamcito.inpost.productsrest.shopping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

  @Override
  public EntityModel<Product> toModel(Product product) {

    return EntityModel.of(product,
        linkTo(methodOn(ShoppingController.class).getProduct(product.getId())).withSelfRel(),
        linkTo(methodOn(ShoppingController.class).getQuote(product.getId(), null)).withRel("quote"),
        linkTo(methodOn(ShoppingController.class).getAllProducts()).withRel("products"));
  }
}
