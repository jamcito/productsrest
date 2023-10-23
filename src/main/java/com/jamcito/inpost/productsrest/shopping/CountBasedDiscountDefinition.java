package com.jamcito.inpost.productsrest.shopping;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CountBasedDiscountDefinition {
    @NotNull
    @Min(value = 0, message = "discountAmount must be non-negative")
    private Integer discountAmount;

    public CountBasedDiscountDefinition(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }
}
