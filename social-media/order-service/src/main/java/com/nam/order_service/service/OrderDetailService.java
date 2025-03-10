package com.nam.order_service.service;

import com.nam.order_service.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {
    void createOrderDetail(List<OrderDetailDTO> orderDetails, String orderId);
}
