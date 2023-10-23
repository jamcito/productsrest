package com.jamcito.inpost.productsrest.shopping;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jamcito.inpost.productsrest.shopping.model.product.Product;

interface ProductRepository extends JpaRepository<Product, UUID> {
}