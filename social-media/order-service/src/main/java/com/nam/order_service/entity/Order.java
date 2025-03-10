package com.nam.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String customerName;

    private String address;

    private String phone;

    private Long totalPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private OrderStatus status;

    private String statusText;

    private String paymentId;

    private String customerId;
}
