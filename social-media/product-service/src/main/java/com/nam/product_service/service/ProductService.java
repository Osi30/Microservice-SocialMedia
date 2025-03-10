package com.nam.product_service.service;

import com.nam.product_service.dto.ProductDTO;
import com.nam.product_service.entity.Product;
import com.nam.product_service.exception.ProductException;

import java.util.List;

public interface ProductService {
    Product findProductById(String id) throws ProductException;
    List<ProductDTO> findAllProducts() throws ProductException;
    ProductDTO createProduct(ProductDTO product) throws ProductException;
    ProductDTO updateProduct(ProductDTO product) throws ProductException;
    void deleteProduct(String id) throws ProductException;
    List<ProductDTO> findProductsByCreator(String creatorId) throws ProductException;
}
