package com.example.controllers;

import com.example.dtos.FakeStoreProductDto;
import com.example.userServiceUtils.UserDto;
import com.example.exceptions.*;
import com.example.models.Product;
import com.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    // ********************* START - AUTHORIZATION USING USER SERVICE ********************* //
    @Autowired
    private RestTemplate restTemplate;  // RestTemplate is used to make HTTP requests to user service API

    // Get Product with given Id with Authentication Token in Header - This is a protected route and requires a valid token to access product data with given Id
    @GetMapping("/auth/{id}")
    public ResponseEntity<Product> getProductByIdAuth(@PathVariable("id") long id, @RequestHeader("token") String token) throws HttpClientErrorException, ProductNotFoundException {
        // Validate the token by calling the User Service API with the token in the header and get the user details if token is valid
        // else HttpClientErrorException is thrown as Status code returned is UNAUTHORIZED, this is handled by ExceptionHandlers
        restTemplate.getForObject("http://localhost:8080/user/validate/"+token, UserDto.class);
        // If token is valid then no exception is thrown, return the product with the given id
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }
    // ********************* END - AUTHORIZATION USING USER SERVICE ********************* //


    // Get Product with given Id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) throws ProductNotFoundException {
        // Use ResponseEntity as returnType because we want to return a response with status code, if Product is null then we need to specify different status code
        Product product = productService.getProductById(id);
        // If product is null then it throws ProductNotFoundException which is handled by ExceptionHandlers
        // Else return OK status code along with product
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    // Get all the Products list
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        // Get all the products from the Fake Store API
        List<Product> products = productService.getAllProducts();
        // If products is null or empty then NullPointerException is thrown which is handled by ExceptionHandlers
        // Else return OK status code along with products list
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    // Get all the Categories list
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        // Get all the categories from the Fake Store API
        List<String> categories = productService.getAllCategories();
        // If products is null or empty then NullPointerException is thrown which is handled by ExceptionHandlers
        // Else return OK status code along with categories list
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    // Get all the Products with given Category
    @GetMapping("/categories/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("category") String category) {
        // Get all the products with the given category from the Fake Store API
        List<Product> products = productService.getProductsByCategory(category);
        // If products is null or empty then NullPointerException is thrown which is handled by ExceptionHandlers
        // Else return OK status code along with products list
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    // Replace Product with given Id
    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") long id, @RequestBody FakeStoreProductDto fakeStoreProductDto) {
        // Replace the product with the given id with the new product data from fakeStoreProductDto
        Product product = productService.replaceProduct(id, fakeStoreProductDto);
        // If product is null i.e. no product found to replace or error occurred then return NO_CONTENT status code
        if(product == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Else return OK status code along with product
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    // Update Product with given Id
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody FakeStoreProductDto fakeStoreProductDto) {
        // Update the product with the given id with the new product data from fakeStoreProductDto
        Product product = productService.updateProduct(id, fakeStoreProductDto);
        // If product is null i.e. no product found to update or error occurred then return NO_CONTENT status code
        if(product == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Else return OK status code along with product
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    // Create a Product
    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody FakeStoreProductDto fakeStoreProductDto) {
        // Create a new product with the data from fakeStoreProductDto
        Product product = productService.createProduct(fakeStoreProductDto);
        // If product is null i.e. error occurred then return NO_CONTENT status code
        if(product == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Else return CREATED status code along with product
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }


    // Delete Product with given Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") long id) {
        // Delete the product with the given id
        Product product = productService.deleteProduct(id);
        // If product is null i.e. no product found to delete or error occurred then return NO_CONTENT status code
        if(product == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Else return OK status code along with product
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
