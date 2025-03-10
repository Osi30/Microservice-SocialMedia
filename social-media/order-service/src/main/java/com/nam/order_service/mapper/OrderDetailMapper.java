package com.nam.order_service.mapper;

import com.nam.order_service.dto.OrderDetailDTO;
import com.nam.order_service.entity.OrderDetail;

public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailDTO orderDetail);
}
