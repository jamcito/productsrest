package com.jamcito.inpost.productsrest.shopping;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<Product, UUID> {
}