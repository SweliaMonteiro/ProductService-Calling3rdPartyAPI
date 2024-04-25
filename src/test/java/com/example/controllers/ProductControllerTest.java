package com.example.controllers;

import com.example.exceptions.ProductNotFoundException;
import com.example.models.Product;
import com.example.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    // @MockBean annotation is used to mock the ProductService class
    @MockBean
    private ProductService productService;


    @Test
    void validGetProductById() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");
        product.setDescription("Product 1 Description");
        product.setPrice(100.0);

        // Mocking the getProductById method of ProductService to return the product object
        when(productService.getProductById(1L)).thenReturn(product);
        // Calling the getProductById method of ProductController to get the product object
        Product actualProduct = productController.getProductById(1L).getBody();
        // Asserting the expected product object with the actual product object returned by the getProductById method of ProductController
        // If both are same then the test case will pass otherwise it will fail
        assertEquals(product, actualProduct);
    }


    @Test
    void invalidGetProductById() throws ProductNotFoundException {
        // Mocking the getProductById method of ProductService to return null
        when(productService.getProductById(100L)).thenReturn(null);
        // Calling the getProductById method of ProductController to get the product object
        // As the product object is null, it will throw ProductNotFoundException
        assertThrows(ProductNotFoundException.class, () -> productController.getProductById(100L));
    }


    @Test
    void getAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");
        product1.setDescription("Product 1 Description");
        product1.setPrice(100.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Product 2");
        product2.setDescription("Product 2 Description");
        product2.setPrice(200.0);

        // Mocking the getAllProducts method of ProductService to return a list of products
        when(productService.getAllProducts()).thenReturn(List.of(product1, product2));
        // Calling the getAllProducts method of ProductController to get the list of products
        List<Product> actualProducts = productController.getAllProducts().getBody();
        // Asserting the expected list of products with the actual list of products returned by the getAllProducts method of ProductController
        // If both are same then the test case will pass otherwise it will fail
        assertEquals(List.of(product1, product2), actualProducts);
    }
}