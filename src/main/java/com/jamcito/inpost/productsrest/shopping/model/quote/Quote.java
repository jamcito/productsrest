package com.jamcito.inpost.productsrest.shopping.model.quote;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.jamcito.inpost.productsrest.shopping.model.product.Product;

public class Quote extends RepresentationModel<Quote> {
    private UUID productId;
    private Integer quoteCents;
    private Integer count;

    public Quote(Product product, Integer count) {
        this.productId = product.getId();
        this.count = count;
        switch (product.getDiscountPolicy()) {
            case COUNT_BASED:
                this.quoteCents = Math.max(0, (product.getBasePriceCents() - product.getDiscountFactor()) * count);
                break;
            case PERCENTAGE:
                this.quoteCents = product.getBasePriceCents() * count * (100 - product.getDiscountFactor()) / 100;
                break;
        }
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuoteCents() {
        return quoteCents;
    }

    public Integer getCount() {
        return count;
    }
}
