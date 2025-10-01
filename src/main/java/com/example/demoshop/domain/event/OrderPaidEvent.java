package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderPaidEvent(
        String orderId,
        Instant paidAt
) {
    public OrderPaidEvent(String orderId) {
        this(orderId, Instant.now());
    }
}
