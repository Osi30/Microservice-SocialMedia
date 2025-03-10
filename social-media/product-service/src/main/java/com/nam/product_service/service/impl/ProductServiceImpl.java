package com.nam.product_service.service.impl;

import com.nam.product_service.dto.ProductDTO;
import com.nam.product_service.entity.Product;
import com.nam.product_service.entity.ProductStatus;
import com.nam.product_service.exception.ProductException;
import com.nam.product_service.mapper.ProductMapper;
import com.nam.product_service.repository.ProductRepository;
import com.nam.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product findProductById(String id) throws ProductException {
        return productRepository.findById(id).orElseThrow(() -> new ProductException("Product not found with id: " + id));
    }

    @Override
    public List<ProductDTO> findAllProducts() throws ProductException {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            productDTOS.add(productMapper.toProductDTO(product));
        }
        return productDTOS;
    }

    @Override
    public ProductDTO createProduct(ProductDTO product) throws ProductException {
        Product newProduct = productMapper.toProduct(product);
        newProduct.setProductStatus(ProductStatus.AVAILABLE);
        newProduct.setProductStatusText(getProductStatusText(newProduct.getProductStatus()));
        Product savedProduct = productRepository.save(newProduct);
        return productMapper.toProductDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) throws ProductException {
        Product existedProduct = findProductById(productDTO.getId());
        Product updatedProduct = productMapper.updateProduct(existedProduct, productDTO);
        return productMapper.toProductDTO(productRepository.save(updatedProduct));
    }

    @Override
    public void deleteProduct(String id) throws ProductException {
        Product product = findProductById(id);
        product.setProductStatus(ProductStatus.UNAVAILABLE);
        product.setProductStatusText(getProductStatusText(product.getProductStatus()));
        productRepository.save(product);
    }

    @Override
    public List<ProductDTO> findProductsByCreator(String creatorId) throws ProductException {
        List<ProductDTO> productDTOS = findAllProducts();
        return productDTOS.stream().filter(product -> product.getCreatorId().equals(creatorId)).toList();
    }

    private String getProductStatusText(ProductStatus productStatus) {
        return switch (productStatus) {
            case AVAILABLE -> "Product is available";
            case OUT_OF_DATE -> "Product is out of date";
            case OUT_OF_STOCK -> "Product is out of stock";
            case UNAVAILABLE -> "Product is unavailable";
        };
    }
}
