package com.example.demoshop.domain.event;

public class OrderReturnedEvent {
    private final String orderId;

    public OrderReturnedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
