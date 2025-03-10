package com.nam.order_service.service.impl;

import com.nam.order_service.dto.OrderDTO;
import com.nam.order_service.dto.OrderDetailDTO;
import com.nam.order_service.entity.Order;
import com.nam.order_service.entity.OrderDetail;
import com.nam.order_service.entity.OrderStatus;
import com.nam.order_service.exception.OrderException;
import com.nam.order_service.mapper.OrderMapper;
import com.nam.order_service.repository.OrderDetailRepository;
import com.nam.order_service.repository.OrderRepository;
import com.nam.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) throws OrderException {
        Order order = orderMapper.toOrder(orderDTO);
        order.setStatus(OrderStatus.AWAITING_PAYMENT);
        order.setStatusText(getOrderTextByStatusId(OrderStatus.AWAITING_PAYMENT));
        // Not customer Id, Customer Name
        Order savedOrder = orderRepository.save(order);
        saveOrderDetails(orderDTO.getOrderDetails(), savedOrder.getId());
        return orderMapper.toOrderDTO(savedOrder);
    }


    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) throws OrderException {
        if (orderDTO.getStatus().equals(OrderStatus.AWAITING_PAYMENT)) {
            Order order = orderMapper.updateOrder(getOrderById(orderDTO.getId()), orderDTO);
            orderRepository.save(order);
            return orderMapper.toOrderDTO(order);
        }
        return null;
    }

    @Override
    public Order getOrderById(String orderId) throws OrderException {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderException("Order not found with id: " + orderId));
    }

    @Override
    public List<OrderDTO> getAllOrders() throws OrderException {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            orderDTOS.add(orderMapper.toOrderDTO(order));
        }
        return orderDTOS;
    }

    @Override
    public void deleteOrder(String orderId) throws OrderException {
        Order order = getOrderById(orderId);
        // check the rejector is customer or seller
        order.setStatus(OrderStatus.CANCELLED);
        order.setStatusText(getOrderTextByStatusId(OrderStatus.CANCELLED));
//        order.setStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(String customerId) throws OrderException {
        return getAllOrders().stream().filter(o -> o.getCustomerId().equals(customerId)).toList();
    }

    private void saveOrderDetails(List<OrderDetailDTO> orderDetailDTO, String orderId) throws OrderException {
        for (OrderDetailDTO orderDetailInList : orderDetailDTO) {
            orderDetailRepository.save(OrderDetail.builder()
                    .orderId(orderId)
                    .productId(orderDetailInList.getProductId())
                    .quantity(orderDetailInList.getQuantity())
                    .build());
        }
    }

    private String getOrderTextByStatusId(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case AWAITING_PAYMENT -> "Order Awaiting Payment";
            case AWAITING_VERIFICATION -> "Order Awaiting Verification";
            case AWAITING_DELIVERY -> "Order Awaiting Delivery";
            case DELIVERED -> "Order Delivered";
            case CANCELLED -> "Order Cancelled";
            case FAILED -> "Order Failed";
            case RECEIVED -> "Order Received";
            case REJECTED -> "Order Rejected";
            case SUCCESS -> "Order Success";
        };
    }
}
