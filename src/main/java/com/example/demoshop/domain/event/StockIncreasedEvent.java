package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;


import java.time.Instant;

public class StockIncreasedEvent {
    private final Product.ProductId productId;
    private final int quantity;
    private final Instant occurredAt;

    public StockIncreasedEvent(Product.ProductId productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.occurredAt = Instant.now();
    }

    public Product.ProductId productId() { return productId; }
    public int quantity() { return quantity; }
    public Instant occurredAt() { return occurredAt; }
}
