package com.jamcito.inpost.productsrest.shopping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jamcito.inpost.productsrest.shopping.model.product.ProductInputDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShoppingService service;

    private ObjectWriter objectWriter;

    public ShoppingControllerTest() {
        this.objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Test
    public void testPostProductsInvalidPrice() throws Exception {
        ProductInputDTO product = new ProductInputDTO(10, -1);

        mvc.perform(post("/products")
                .contentType("application/json")
                .content(objectWriter.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostProductsInvalidCount() throws Exception {
        ProductInputDTO product = new ProductInputDTO(-1, 1);

        mvc.perform(post("/products")
                .contentType("application/json")
                .content(objectWriter.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutCountDiscountInvalid() throws Exception {
        UUID id = new UUID(1, 2);

        mvc.perform(put("/products/{id}/count-discount", id)
                .contentType("application/json")
                .content("-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutPercentageDiscountInvalidMin() throws Exception {
        UUID id = new UUID(1, 2);

        mvc.perform(put("/products/{id}/percentage-discount", id)
                .contentType("application/json")
                .content("-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutPercentageDiscountInvalidMax() throws Exception {
        UUID id = new UUID(1, 2);

        mvc.perform(put("/products/{id}/percentage-discount", id)
                .contentType("application/json")
                .content("101"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutProductsInvalidCount() throws Exception {
        UUID id = new UUID(1, 2);

        ProductInputDTO product = new ProductInputDTO(-1, 1);

        mvc.perform(put("/products/{id}", id)
                .contentType("application/json")
                .content(objectWriter.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutProductsInvalidPrice() throws Exception {
        UUID id = new UUID(1, 2);

        ProductInputDTO product = new ProductInputDTO(1, -1);

        mvc.perform(put("/products/{id}", id)
                .contentType("application/json")
                .content(objectWriter.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }
}
