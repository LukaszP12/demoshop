package example.demoshop.domain.event;

import java.time.Instant;

public record OrderPaidEvent(
        String orderId,
        Instant paidAt
) {
    public OrderPaidEvent(String orderId) {
        this(orderId, Instant.now());
    }
}
