package com.nam.order_service.mapper.impl;

import com.nam.order_service.dto.OrderDTO;
import com.nam.order_service.entity.Order;
import com.nam.order_service.mapper.OrderMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toOrder(OrderDTO orderDTO) {
        return Order.builder()
                .customerId(orderDTO.getCustomerId())
                .customerName(orderDTO.getCustomerName())
                .address(orderDTO.getAddress())
                .phone(orderDTO.getPhone())
                .build();
    }

    @Override
    public OrderDTO toOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .customerName(order.getCustomerName())
                .address(order.getAddress())
                .phone(order.getPhone())
                .status(order.getStatus())
                .statusText(order.getStatusText())
                .paymentId(order.getPaymentId())
                .createdAt(order.getCreatedAt())
                .build();
    }

    @Override
    public Order updateOrder(Order order, OrderDTO orderDTO) {
        if (orderDTO.getCustomerName() != null && !orderDTO.getCustomerName().equals(order.getCustomerName())) {
            order.setCustomerName(orderDTO.getCustomerName());
        }
        if (orderDTO.getAddress() != null && !orderDTO.getAddress().equals(order.getAddress())) {
            order.setAddress(orderDTO.getAddress());
        }
        if (orderDTO.getPhone() != null && !orderDTO.getPhone().equals(order.getPhone())) {
            order.setPhone(orderDTO.getPhone());
        }
        if (orderDTO.getStatus() != null && !orderDTO.getStatus().equals(order.getStatus())) {
            order.setStatus(orderDTO.getStatus());
        }
        return order;
    }
}
