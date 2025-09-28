package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import java.time.Instant;

public class OrderPlacedEvent {

    private final String orderId;
    private final String userId;
    private final Instant occurredAt;

    public OrderPlacedEvent(String orderId, String userId) {
        this.orderId = orderId;
        this.userId = userId;
        this.occurredAt = Instant.now();
    }

    public String orderId() {
        return orderId;
    }

    public String userId() {
        return userId;
    }

    public Instant occurredAt() {
        return occurredAt;
    }
}
