package com.nam.order_service.service.impl;

import com.nam.order_service.dto.OrderDetailDTO;
import com.nam.order_service.entity.OrderDetail;
import com.nam.order_service.mapper.OrderDetailMapper;
import com.nam.order_service.repository.OrderDetailRepository;
import com.nam.order_service.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    public void createOrderDetail(List<OrderDetailDTO> orderDetails, String orderId) {
        for (OrderDetailDTO orderDetail : orderDetails) {
            OrderDetail savedOrderDetail = orderDetailMapper.toOrderDetail(orderDetail);
            savedOrderDetail.setOrderId(orderId);
            orderDetailRepository.save(savedOrderDetail);
        }
    }
}
