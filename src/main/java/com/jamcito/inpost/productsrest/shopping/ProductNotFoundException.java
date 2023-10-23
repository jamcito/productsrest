package com.jamcito.inpost.productsrest.shopping;

import java.util.UUID;

class ProductNotFoundException extends RuntimeException {

    ProductNotFoundException(UUID id) {
        super("Could not find product: " + id);
    }
}