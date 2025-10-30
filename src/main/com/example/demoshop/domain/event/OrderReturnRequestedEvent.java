package example.demoshop.domain.event;

import java.time.Instant;

public record OrderReturnRequestedEvent(
        String orderId,
        Instant requestedAt
) {
    public OrderReturnRequestedEvent(String orderId) {
        this(orderId, Instant.now());
    }
}
