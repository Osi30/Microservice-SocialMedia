package com.nam.order_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nam.order_service.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("total_price")
    private Float totalPrice;

    @JsonProperty("create_at")
    private LocalDateTime createdAt;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("status_text")
    private String statusText;

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("order_details")
    private List<OrderDetailDTO> orderDetails;
}
