package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;

import java.time.Instant;

public class OrderPlacedEvent {

    private final Order.OrderId orderId;
    private final String userId;
    private final Instant occurredAt;

    public OrderPlacedEvent(Order.OrderId orderId, String userId) {
        this.orderId = orderId;
        this.userId = userId;
        this.occurredAt = Instant.now();
    }

    public Order.OrderId orderId() {
        return orderId;
    }

    public String userId() {
        return userId;
    }

    public Instant occurredAt() {
        return occurredAt;
    }
}
