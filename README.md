# Calling third party APIs

## Problem Statement

You are working on a project that requires you to call a third party API. Implement the required functions that will call the Fake Store API behind the scenes to get the work done.

## Requirements
1. Use the following API to get the data: [Fake Store API](https://fakestoreapi.com/docs)
2. Implement the following functions:
    - `getProductById(productId)`: This function should return the details of the product with the given `productId`.
    - `getAllProducts()`: This function should return a list of all the products available in the store.
    - `getAllCategories()`: This function should return a list of all the categories available in the store.
    - `getAllProductsByCategory(category)`: This function should return a list of all the products available in the store that belong to the given `category`.
    - `replaceProduct(productId, product)`: This function should replace the product with the given `productId` with the new product details provided in the `product` object.
    - `updateProduct(productId, product)`: This function should update the product with the given `productId` with the new product details provided in the `product` object.
    - `createProduct(product)`: This function should create a new product with the details provided in the `product` object.
    - `deleteProduct(productId)`: This function should delete the product with the given `productId`.
3. Use @RestController to create the API endpoints and @RequestMapping to map the endpoints to the functions.
4. Use appropriate HTTP methods for each function along with PathVariables and RequestBody where necessary.
5. Use RestTemplate to call the Fake Store APIs.
6. Use @Configuration and @Bean to create a RestTemplate bean.
7. Use @ControllerAdvice and @ExceptionHandler to handle exceptions.
8. Write JUnit tests for each function. You can use @MockBean to mock the RestTemplate calls and only test the functionality of your functions. 
