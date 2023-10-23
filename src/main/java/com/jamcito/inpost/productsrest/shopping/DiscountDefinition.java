package com.jamcito.inpost.productsrest.shopping;

public class DiscountDefinition {
    private Integer discountFactor;

    public DiscountDefinition(Integer discountAmount){
        this.discountFactor = discountAmount;
    }

    public Integer getDiscountFactor(){
        return discountFactor;
    }
}
