package com.jamcito.inpost.productsrest.shopping;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

public class Quote extends RepresentationModel<Quote> {
    private UUID productId;
    private Integer quote;
    private Integer count;

    //implementation TBD, magic number;
    private static final Integer DISCOUNT_FACTOR = 10;

    public Quote(Product product, Integer count) {
        this.productId = product.getId();
        this.count = count;
        switch (product.getPolicy()) {
            case FLAT:
                this.quote = Math.max(0, (product.getBasePriceCents() - DISCOUNT_FACTOR) * count);
                break;
            case NO_DISCOUNT:
                this.quote = product.getBasePriceCents() * count;
                break;
        }
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuote() {
        return quote;
    }

    public Integer getCount() {
        return count;
    }
}
