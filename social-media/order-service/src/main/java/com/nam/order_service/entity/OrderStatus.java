package com.nam.order_service.entity;

public enum OrderStatus {
    AWAITING_PAYMENT,
    AWAITING_VERIFICATION,
    AWAITING_DELIVERY,
    DELIVERED,
    RECEIVED,
    SUCCESS,
    FAILED,
    REJECTED,
    CANCELLED,
}
