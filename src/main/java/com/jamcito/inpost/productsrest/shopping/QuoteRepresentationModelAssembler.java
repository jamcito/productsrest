package com.jamcito.inpost.productsrest.shopping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jamcito.inpost.productsrest.shopping.model.quote.Quote;

@Component
class QuoteRepresentationModelAssembler implements RepresentationModelAssembler<Quote, RepresentationModel<Quote>> {

    @Override
    public RepresentationModel<Quote> toModel(Quote quote) {

        quote.add(
                linkTo(methodOn(ShoppingController.class).getProduct(quote.getProductId())).withRel("product"),
                linkTo(methodOn(ShoppingController.class).getQuote(quote.getProductId(), quote.getCount()))
                        .withSelfRel());
        return quote;
    }
}
