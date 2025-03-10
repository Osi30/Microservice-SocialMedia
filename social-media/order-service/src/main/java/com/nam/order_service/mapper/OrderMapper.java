package com.nam.order_service.mapper;

import com.nam.order_service.dto.OrderDTO;
import com.nam.order_service.entity.Order;

public interface OrderMapper {
    Order toOrder(OrderDTO orderDTO);
    OrderDTO toOrderDTO(Order order);
    Order updateOrder(Order order, OrderDTO orderDTO);
}
