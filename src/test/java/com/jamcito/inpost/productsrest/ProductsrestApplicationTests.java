package com.jamcito.inpost.productsrest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jamcito.inpost.productsrest.shopping.ShoppingController;
import com.jamcito.inpost.productsrest.shopping.ShoppingService;

@SpringBootTest
public class ProductsRESTApplicationTests {

	@Autowired
	private ShoppingController controller;

	@Autowired
	private ShoppingService service;

	@Test
	void contextLoads() {
		assertNotEquals(controller, null);
		assertNotEquals(service, null);
	}
}
