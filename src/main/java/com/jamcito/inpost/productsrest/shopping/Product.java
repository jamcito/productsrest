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
    private Integer price;

    public Product() {
    }

    public Product(Integer count, Integer price) {
        this.count = count;
        this.price = price;
    }

    public UUID getId() {
        return this.id;
    }

    public Integer getCount() {
        return this.count;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
                && Objects.equals(this.price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.count, this.price);
    }

    @Override
    public String toString() {
        return "Product{id=" + this.id + ", count=" + this.count + ", price=" + this.price + "}";
    }
}