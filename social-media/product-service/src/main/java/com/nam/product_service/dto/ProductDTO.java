package com.nam.product_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nam.product_service.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_description")
    private String productDescription;

    @JsonProperty("product_price")
    private Float productPrice;

    @JsonProperty("product_quantity")
    private Integer productQuantity;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("creator_id")
    private String creatorId;

    @JsonProperty("product_status")
    private String productStatus;

    @JsonProperty("product_status_text")
    private String productStatusText;
}
