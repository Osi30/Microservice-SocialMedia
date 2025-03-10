package com.nam.product_service.mapper.impl;

import com.nam.product_service.dto.ProductDTO;
import com.nam.product_service.entity.Product;
import com.nam.product_service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductDTO productDTO) {
        return Product.builder()
                .productName(productDTO.getProductName())
                .productDescription(productDTO.getProductDescription())
                .productPrice(productDTO.getProductPrice())
                .productQuantity(productDTO.getProductQuantity())
                .creatorId(productDTO.getCreatorId())
                .build();
    }

    @Override
    public ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .createdAt(product.getCreatedAt())
                .productQuantity(product.getProductQuantity())
                .creatorId(product.getCreatorId())
                .productStatus(product.getProductStatus().toString())
                .productStatusText(product.getProductStatusText())
                .build();
    }

    @Override
    public Product updateProduct(Product product, ProductDTO productDTO) {
        if (productDTO.getProductName() != null && !productDTO.getProductName().equals(product.getProductName())) {
            product.setProductName(productDTO.getProductName());
        }
        if (productDTO.getProductDescription() != null && !productDTO.getProductDescription().equals(product.getProductDescription())) {
            product.setProductDescription(productDTO.getProductDescription());
        }
        if (productDTO.getProductPrice() != null && !productDTO.getProductPrice().equals(product.getProductPrice())) {
            product.setProductPrice(productDTO.getProductPrice());
        }
        if (productDTO.getProductQuantity() != null && !productDTO.getProductQuantity().equals(product.getProductQuantity())) {
            product.setProductQuantity(productDTO.getProductQuantity());
        }
        return product;
    }
}
