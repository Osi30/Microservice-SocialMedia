package com.nam.order_service.service;

import com.nam.order_service.config.FeignClientInterceptor;
import com.nam.order_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8005/api/products/",
configuration = FeignClientInterceptor.class)
public interface ProductService {
    @GetMapping("/{id}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable String id);
}
