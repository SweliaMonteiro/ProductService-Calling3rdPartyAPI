package com.example.services;

import com.example.dtos.FakeStoreProductDto;
import com.example.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    void validGetProductById() throws ProductNotFoundException {
        // Create a FakeStoreProductDto object
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(1L);
        fakeStoreProductDto.setTitle("Product 1");
        fakeStoreProductDto.setDescription("Product 1 Description");
        fakeStoreProductDto.setPrice(100.0);

        // Mock the restTemplate.getForObject method to return the FakeStoreProductDto object when called with the given URL
        when(restTemplate.getForObject("https://fakestoreapi.com/products/"+1L, FakeStoreProductDto.class)).thenReturn(fakeStoreProductDto);
        // Assert that the expected FakeStoreProductDto object is equal to the actual Product object returned by the getProductById method of ProductService by comparing their ids
        assertEquals(fakeStoreProductDto.getId(), productService.getProductById(1L).getId());
    }


    @Test
    void invalidGetProductById() {
        // Mock the restTemplate.getForObject method to return null when called with the given URL
        when(restTemplate.getForObject("https://fakestoreapi.com/products/"+100L, FakeStoreProductDto.class)).thenReturn(null);
        // Assert that ProductNotFoundException is thrown when the getProductById method of ProductService is called with an invalid id
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(100L));
    }


    @Test
    void validGetAllCategories() {
        // Create an array of categories
        String[] categories = {"Category 1", "Category 2", "Category 3"};
        // Mock the restTemplate.getForObject method to return the array of categories when called with the given URL
        when(restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class)).thenReturn(categories);
        // Assert that the expected array of categories is equal to the actual List of categories returned by the getAllCategories method of ProductService
        assertEquals(Arrays.stream(categories).toList(), productService.getAllCategories());
    }


    @Test
    void invalidNullListGetAllCategories() {
        // Mock the restTemplate.getForObject method to return null when called with the given URL
        when(restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class)).thenReturn(null);
        // Assert that NullPointerException is thrown when the getAllCategories method of ProductService is called with a null response
        assertThrows(NullPointerException.class, () -> productService.getAllCategories());
    }


    @Test
    void invalidEmptyListGetAllCategories() {
        // Mock the restTemplate.getForObject method to return an empty array when called with the given URL
        when(restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class)).thenReturn(new String[0]);
        // Assert that NullPointerException is thrown when the getAllCategories method of ProductService is called with an empty response
        assertThrows(NullPointerException.class, () -> productService.getAllCategories());
    }


    @Test
    void validUpdateProduct() {
        // Create a FakeStoreProductDto object and update its title
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(1L);
        fakeStoreProductDto.setTitle("Product 1");

        // Mock the restTemplate.patchForObject method to return the FakeStoreProductDto object when called with the given URL
        when(restTemplate.patchForObject("https://fakestoreapi.com/products/"+1L, fakeStoreProductDto, FakeStoreProductDto.class)).thenReturn(fakeStoreProductDto);
        // Assert that the expected FakeStoreProductDto object is equal to the actual Product object returned by the updateProduct method of ProductService by comparing their ids
        assertEquals(fakeStoreProductDto.getId(), productService.updateProduct(1L, fakeStoreProductDto).getId());
        // Assert that the updated title is equal to the title of the Product object returned by the updateProduct method of ProductService
        assertEquals("Product 1", productService.updateProduct(1L, fakeStoreProductDto).getTitle());
    }


    @Test
    void invalidUpdateProduct() {
        // Create a FakeStoreProductDto object
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(1L);
        fakeStoreProductDto.setTitle("Product 1");

        // Mock the restTemplate.patchForObject method to return null when called with the given URL
        when(restTemplate.patchForObject("https://fakestoreapi.com/products/"+100L, fakeStoreProductDto, FakeStoreProductDto.class)).thenReturn(null);
        // Assert that null is returned when the updateProduct method of ProductService is called with an invalid id
        assertNull(productService.updateProduct(100L, fakeStoreProductDto));
    }
}