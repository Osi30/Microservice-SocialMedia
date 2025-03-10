package com.nam.product_service.controller;

import com.nam.gateway.annotation.GatewayOnly;
import com.nam.product_service.dto.ProductDTO;
import com.nam.product_service.dto.UserDTO;
import com.nam.product_service.entity.Product;
import com.nam.product_service.mapper.ProductMapper;
import com.nam.product_service.service.ProductService;
import com.nam.product_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@GatewayOnly
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable String id
    ){
        Product product = productService.findProductById(id);
        ProductDTO productDTO = productMapper.toProductDTO(product);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductDTO productDTO,
            @RequestHeader("Authorization") String token
    ){
        UserDTO creator = userService.getUserProfileByToken(token);
        productDTO.setCreatorId(creator.getId());
        ProductDTO product = productService.createProduct(productDTO);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestBody ProductDTO productDTO
    ){
        ProductDTO product = productService.updateProduct(productDTO);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String id
    ){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByUserId(
            @PathVariable String id
    ){
        List<ProductDTO> productDTOS = productService.findProductsByCreator(id);
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
}
