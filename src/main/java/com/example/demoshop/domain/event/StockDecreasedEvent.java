package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import java.time.Instant;

public class StockDecreasedEvent {
    private final String productId;
    private final int quantity;
    private final Instant occurredAt;

    public StockDecreasedEvent(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.occurredAt = Instant.now();
    }

    public String productId() { return productId; }
    public int quantity() { return quantity; }
    public Instant occurredAt() { return occurredAt; }
}
