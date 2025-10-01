package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import java.time.Instant;

public record OrderShippedEvent(
        String orderId,
        Instant shippedAt
) {
    public OrderShippedEvent(String orderId) {
        this(orderId, Instant.now());
    }
}
