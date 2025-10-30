package com.example.demoshop.domain.event;

import java.time.Instant;

public record OrderDeliveredEvent(
        String orderId,
        Instant deliveredAt
) {
    public OrderDeliveredEvent(String orderId) {
        this(orderId, Instant.now());
    }
}
