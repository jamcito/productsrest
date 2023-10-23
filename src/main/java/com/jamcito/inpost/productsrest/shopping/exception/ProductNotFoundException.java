package com.jamcito.inpost.productsrest.shopping.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID id) {
        super("Could not find product: " + id);
    }
}