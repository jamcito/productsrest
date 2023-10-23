package com.jamcito.inpost.productsrest.shopping;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductInputDTO {
    @NotNull
    @Min(value = 0, message = "count must be non-negative")
    private Integer count;

    @NotNull
    @Min(value = 0, message = "basePriceCents must be non-negative")
    private Integer basePriceCents;

    public ProductInputDTO(Integer count, Integer basePriceCents) {
        this.count = count;
        this.basePriceCents = basePriceCents;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getBasePriceCents() {
        return basePriceCents;
    }
}
