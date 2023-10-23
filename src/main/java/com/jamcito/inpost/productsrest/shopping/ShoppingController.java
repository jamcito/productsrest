package com.jamcito.inpost.productsrest.shopping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingController {
    @GetMapping("/")
	public String index() {
		return "GET called";
	}
}
