package com.example.services;

import com.example.dtos.FakeStoreProductDto;
import com.example.exceptions.*;
import com.example.models.*;
import java.util.*;

public interface ProductService {

    Product getProductById(long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    List<String> getAllCategories();

    List<Product> getProductsByCategory(String category);

    Product replaceProduct(long id, FakeStoreProductDto fakeStoreProductDto);

    Product updateProduct(long id, FakeStoreProductDto fakeStoreProductDto);

    Product createProduct(FakeStoreProductDto fakeStoreProductDto);

    Product deleteProduct(long id);

}
