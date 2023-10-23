package com.jamcito.inpost.productsrest.shopping.exception;

import java.util.UUID;

public class InsufficientProductCountException extends RuntimeException {

    public InsufficientProductCountException(UUID id) {
        super("Insufficient count of product: " + id + " in stock.");
    }
}