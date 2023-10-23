package com.jamcito.inpost.productsrest.shopping;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Product {

    private @Id @GeneratedValue UUID id;
    private Integer count;
    private Integer basePriceCents;
    private DiscountPolicy discountPolicy;
    private Integer discountFactor;

    public Product() {
    }

    public Product(Integer count, Integer basePriceCents) {
        this(count, basePriceCents, DiscountPolicy.COUNT_BASED, 0);
    }

    public Product(Integer count, Integer basePriceCents, DiscountPolicy policy, Integer discountFactor) {
        this.count = count;
        this.basePriceCents = basePriceCents;
        this.discountPolicy = policy;
        this.discountFactor = discountFactor;
    }

    public UUID getId() {
        return this.id;
    }

    public Integer getCount() {
        return this.count;
    }

    public Integer getBasePriceCents() {
        return this.basePriceCents;
    }

    public DiscountPolicy getDiscountPolicy() {
        return this.discountPolicy;
    }

    public Integer getDiscountFactor() {
        return this.discountFactor;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setBasePriceCents(Integer basePriceCents) {
        this.basePriceCents = basePriceCents;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public void setDiscountFactor(Integer discountFactor) {
        this.discountFactor = discountFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        Product product = (Product) o;
        return Objects.equals(this.id, product.id)
                && Objects.equals(this.count, product.count)
                && Objects.equals(this.basePriceCents, product.basePriceCents)
                && Objects.equals(this.discountPolicy, product.discountPolicy)
                && Objects.equals(this.discountFactor, product.discountFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.count, this.basePriceCents, this.discountPolicy, this.discountFactor);
    }

    @Override
    public String toString() {
        return "Product{id=" + this.id + ", count=" + this.count + ", basePriceCents=" + this.basePriceCents
                + ", discountPolicy="
                + this.discountPolicy + ", discountFactor=" + discountFactor + "}";
    }
}