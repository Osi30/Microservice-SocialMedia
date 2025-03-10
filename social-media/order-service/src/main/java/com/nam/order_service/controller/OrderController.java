package com.nam.order_service.controller;

import com.nam.gateway.annotation.GatewayOnly;
import com.nam.order_service.dto.OrderDTO;
import com.nam.order_service.dto.OrderDetailDTO;
import com.nam.order_service.dto.ProductDTO;
import com.nam.order_service.dto.UserDTO;
import com.nam.order_service.entity.Order;
import com.nam.order_service.exception.OrderException;
import com.nam.order_service.mapper.OrderMapper;
import com.nam.order_service.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@GatewayOnly
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final ProductService productService;
    private final OrderDetailService orderDetailService;
    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(
            @PathVariable String id
    ) {
        Order order = orderService.getOrderById(id);
        OrderDTO orderDTO = orderMapper.toOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(
            @RequestBody OrderDTO orderDTO,
            @RequestHeader("Authorization") String token
    ) throws UnsupportedEncodingException {
        UserDTO customer = userService.getUserProfileByToken(token);
        List<ProductDTO> products = getProductFromOrderDetailList(orderDTO.getOrderDetails());
        Map<String, List<ProductDTO>> orderLists = products.stream().collect(Collectors.groupingBy(ProductDTO::getCreatorId));
        OrderDTO order = new OrderDTO();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (var entry : orderLists.entrySet()) {
            if (entry.getValue().get(0).getCreatorId().equals(customer.getId())) {
                throw new OrderException("Customer cannot order their products");
            }
            orderDTO.setCustomerId(customer.getId());
            order = orderService.createOrder(orderDTO);
            orderDTOS.add(order);
            orderDetailService.createOrderDetail(orderDTO.getOrderDetails(), order.getId());
        }
        String paymentURL = paymentService.createPaymentUrl(orderDTOS, orderDTO.getTotalPrice());
        return new ResponseEntity<>(paymentURL, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<OrderDTO> updateOrder(
            @RequestBody OrderDTO orderDTO
    ) {
        OrderDTO order = orderService.updateOrder(orderDTO);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable String id
    ) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/vnpay/callback")
    public ResponseEntity<Void> paymentCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private List<ProductDTO> getProductFromOrderDetailList(List<OrderDetailDTO> orderDetails) {
        List<ProductDTO> products = new ArrayList<>();
        for (OrderDetailDTO orderDetailDTO : orderDetails) {
            products.add(productService.getProductById(orderDetailDTO.getProductId()).getBody());
        }
        return products;
    }
}
