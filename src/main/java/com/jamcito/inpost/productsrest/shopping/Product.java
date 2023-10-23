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
    private DiscountPolicy policy;

    public Product() {
    }

    public Product(Integer count, Integer basePriceCents) {
        this(count, basePriceCents, DiscountPolicy.NO_DISCOUNT);
    }

    public Product(Integer count, Integer basePriceCents, DiscountPolicy policy) {
        this.count = count;
        this.basePriceCents = basePriceCents;
        this.policy = policy;
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

    public DiscountPolicy getPolicy() {
        return this.policy;
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

    public void setPolicy(DiscountPolicy policy) {
        this.policy = policy;
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
                && Objects.equals(this.policy, product.policy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.count, this.basePriceCents, this.policy);
    }

    @Override
    public String toString() {
        return "Product{id=" + this.id + ", count=" + this.count + ", basePriceCents=" + this.basePriceCents + ", discountPolicy="
                + this.policy + "}";
    }
}