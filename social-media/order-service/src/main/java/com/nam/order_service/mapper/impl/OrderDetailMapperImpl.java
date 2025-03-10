package com.nam.order_service.mapper.impl;

import com.nam.order_service.dto.OrderDetailDTO;
import com.nam.order_service.entity.OrderDetail;
import com.nam.order_service.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public OrderDetail toOrderDetail(OrderDetailDTO orderDetail) {
        return OrderDetail.builder()
                .productId(orderDetail.getProductId())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}
