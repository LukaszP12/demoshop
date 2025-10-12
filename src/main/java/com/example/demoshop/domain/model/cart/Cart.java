package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart;

import main.java.com.example.demoshop.java.com.example.demoshop.application.cart.ShippingPolicy;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.ProductId;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private ShippingPolicy shippingPolicy;
    private final String userId;
    private final Map<ProductId, CartItem> items = new HashMap<>();
    private Instant lastUpdated;

    public Cart(String userId) {
        this.userId = userId;
        this.lastUpdated = Instant.now();
    }

    public String userId() { return userId; }
    public Instant lastUpdated() { return lastUpdated; }
    public Collection<CartItem> items() { return items.values(); }

    public void addProduct(Product product, int quantity) {
        CartItem item = items.get(product.getId());
        if (item != null) {
            item.increaseQuantity(quantity);
        } else {
            items.put(product.getId(), new CartItem(product, quantity,product.getPrice()));
        }
        touch();
    }

    public void removeItem(ProductId productId) {
        items.remove(productId);
        touch();
    }

    private void touch() {
        this.lastUpdated = Instant.now();
    }

    public boolean isExpired(long expirationSeconds) {
        return Instant.now().isAfter(lastUpdated.plusSeconds(expirationSeconds));
    }

    public Money total() {
        return items.values().stream()
                .map(CartItem::subtotal)
                .reduce(Money.zero("USD"), Money::add);
    }

    public void addItem(CartItem item) {
        items.merge(item.productId(), item, (existing, incoming) -> {
            existing.setQuantity(existing.quantity() + incoming.quantity());
            return existing;
        });
    }

    // --- Restoration methods for snapshots ---
    void restoreItem(CartItem item) {
        items.put(item.productId(), item);
    }

    void restoreLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public static Cart fromSnapshot(CartSnapshot snapshot) {
        Cart cart = new Cart(snapshot.userId());
        snapshot.items().forEach(cart::restoreItem);
        cart.restoreLastUpdated(snapshot.lastUpdated());
        return cart;
    }

    public void clearItems() {
        items.clear();
    }
}
