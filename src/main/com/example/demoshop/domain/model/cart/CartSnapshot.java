package example.demoshop.domain.model.cart;

import example.demoshop.domain.model.common.Money;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class CartSnapshot {

    private final String userId;
    private final List<CartItem> items;
    private final Money total;
    private final Instant createdAt;
    private final Instant lastUpdated;

    public CartSnapshot(Cart cart) {
        this.userId = cart.userId();
        this.items = List.copyOf(cart.items());
        this.total = cart.total();
        this.createdAt = Instant.now();
        this.lastUpdated = cart.lastUpdated();
    }

    public String userId() { return userId; }
    public List<CartItem> items() { return Collections.unmodifiableList(items); }
    public Money total() { return total; }
    public Instant createdAt() { return createdAt; }
    public Instant lastUpdated() { return lastUpdated; }
}
