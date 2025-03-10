package com.nam.order_service.service;

import com.nam.order_service.dto.OrderDTO;
import com.nam.order_service.entity.Order;
import com.nam.order_service.exception.OrderException;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO) throws OrderException;
    OrderDTO updateOrder(OrderDTO orderDTO) throws OrderException;
    Order getOrderById(String orderId) throws OrderException;
    List<OrderDTO> getAllOrders() throws OrderException;
    void deleteOrder(String orderId) throws OrderException;
    List<OrderDTO> getOrdersByCustomerId(String customerId) throws OrderException;
}
