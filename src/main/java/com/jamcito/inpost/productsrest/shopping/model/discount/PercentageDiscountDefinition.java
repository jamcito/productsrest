package com.jamcito.inpost.productsrest.shopping.model.discount;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PercentageDiscountDefinition {
    @NotNull
    @Min(value = 0, message = "discountAmount must be non-negative")
    @Max(value = 100, message = "discountAmount must be less than or equal to 100")
    private Integer discountPercentage;

    public PercentageDiscountDefinition(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }
}
