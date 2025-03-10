package com.nam.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String productName;

    private String productDescription;

    private Float productPrice;

    private Integer productQuantity;

//    private String productImageUrl;

    private ProductStatus productStatus;

    private String productStatusText;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String creatorId;
}
