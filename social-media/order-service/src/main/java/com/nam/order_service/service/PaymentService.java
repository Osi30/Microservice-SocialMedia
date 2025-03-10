package com.nam.order_service.service;

import com.nam.order_service.dto.OrderDTO;
import com.nam.order_service.dto.UserDTO;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface PaymentService {
    String createPaymentUrl(List<OrderDTO> orderDTOS, float totalMoney) throws UnsupportedEncodingException;
}
