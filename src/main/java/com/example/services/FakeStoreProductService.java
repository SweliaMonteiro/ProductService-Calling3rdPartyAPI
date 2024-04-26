package com.example.services;

import com.example.dtos.*;
import com.example.exceptions.*;
import com.example.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import java.util.*;


@Service
public class FakeStoreProductService implements ProductService {

    @Autowired
    private RestTemplate restTemplate;


    public Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImage(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setDescription(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }


    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        // Call Fake Store API to get product data with the given id, convert the responseType query parameter to FakeStoreProductDto
        FakeStoreProductDto response = restTemplate.getForObject("https://fakestoreapi.com/products/"+id, FakeStoreProductDto.class);
        if(response == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        // Convert response to Product
        return convertFakeStoreProductDtoToProduct(response);
    }


    @Override
    public List<Product> getAllProducts() {
        // Call Fake Store API to get all products, convert the responseType to Array of FakeStoreProductDto because if you use List then Generics are erased at runtime
        FakeStoreProductDto[] responseList = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        if(responseList == null || responseList.length == 0) {
            throw new NullPointerException("No products found");
        }
        // Convert all elements of responseList to List of Product
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto response:responseList) {
            products.add(convertFakeStoreProductDtoToProduct(response));
        }
        return products;
    }


    @Override
    public List<String> getAllCategories() {
        // Call Fake Store API to get all categories, convert the responseType to Array of String because if you use List then Generics are erased at runtime
        String[] responseList = restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class);
        if(responseList == null || responseList.length == 0) {
            throw new NullPointerException("No categories found");
        }
        // Convert responseList to List of String
        return Arrays.asList(responseList);
    }


    @Override
    public List<Product> getProductsByCategory(String category) {
        // Call Fake Store API to get all products of the given category, convert the responseType to Array of FakeStoreProductDto because if you use List then Generics are erased at runtime
        FakeStoreProductDto[] responseList = restTemplate.getForObject("https://fakestoreapi.com/products/category/"+category, FakeStoreProductDto[].class);
        if(responseList == null || responseList.length == 0) {
            throw new NullPointerException("No products found for category " + category);
        }
        // Convert all elements of responseList to List of Product
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto response:responseList) {
            products.add(convertFakeStoreProductDtoToProduct(response));
        }
        return products;
    }


    @Override
    public Product replaceProduct(long id, FakeStoreProductDto fakeStoreProductDto) {
        // Copied this part of code from RestTemplate class : <T> T patchForObject(String url, @Nullable Object request, Class<T> responseType)
        // Just replace the returnType with FakeStoreProductDto.class and call all the methods using restTemplate
        // put() method in restTemplate doesn't return any data, but we need to return the updated Product. If we use put() method then we need to call getForObject() method again to get the updated data
        // Therefore for one request there will be 2 API calls made which will increase the latency. Hence, just copy the code and do the modifications as required
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.PUT, requestCallback, responseExtractor);
        if(response == null) {
            return null;
        }
        // Convert response to Product
        return convertFakeStoreProductDtoToProduct(response);
    }


    @Override
    public Product updateProduct(long id, FakeStoreProductDto fakeStoreProductDto) {
        // Call Fake Store API to update product with the given id and given dto data, convert the responseType query parameter to FakeStoreProductDto
        FakeStoreProductDto response = restTemplate.patchForObject("https://fakestoreapi.com/products/"+id, fakeStoreProductDto, FakeStoreProductDto.class);
        if(response == null) {
            return null;
        }
        // Convert response to Product
        return convertFakeStoreProductDtoToProduct(response);
    }


    @Override
    public Product createProduct(FakeStoreProductDto fakeStoreProductDto) {
        // Call Fake Store API to create a new product with the given dto data, convert the responseType to FakeStoreProductDto
        FakeStoreProductDto response = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreProductDto, FakeStoreProductDto.class);
        if(response == null) {
            return null;
        }
        // Convert response to Product
        return convertFakeStoreProductDtoToProduct(response);
    }


    @Override
    public Product deleteProduct(long id) {
        // Copied this part of code from RestTemplate class : <T> T patchForObject(String url, @Nullable Object request, Class<T> responseType)
        // Just replace the returnType with FakeStoreProductDto.class and call all the methods using restTemplate
        // delete() method in restTemplate doesn't return any data, but we need to return the deleted Product. If we use delete() method then then we won't be able to return the deleted Product.
        // Hence, just copy the code and do the modifications as required
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.DELETE, requestCallback, responseExtractor);
        if(response == null) {
            return null;
        }
        // Convert response to Product
        return convertFakeStoreProductDtoToProduct(response);
    }

}
