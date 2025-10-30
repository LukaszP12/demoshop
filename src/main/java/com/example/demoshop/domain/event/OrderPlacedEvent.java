package com.example.demoshop.domain.event;

import java.time.Instant;

public record OrderPlacedEvent(
        String orderId,
        Instant placedAt
) {
    public OrderPlacedEvent(String orderId) {
        this(orderId, Instant.now());
    }
}
