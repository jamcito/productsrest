package com.jamcito.inpost.productsrest.shopping;

import java.util.UUID;

class InsufficientProductCountException extends RuntimeException {

    InsufficientProductCountException(UUID id) {
        super("Insufficient count of product: " + id + " in stock.");
    }
}