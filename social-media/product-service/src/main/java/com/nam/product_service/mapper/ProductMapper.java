package com.nam.product_service.mapper;

import com.nam.product_service.dto.ProductDTO;
import com.nam.product_service.entity.Product;

public interface ProductMapper {
    Product toProduct(ProductDTO productDTO);
    ProductDTO toProductDTO(Product product);
    Product updateProduct(Product product, ProductDTO productDTO);
}
