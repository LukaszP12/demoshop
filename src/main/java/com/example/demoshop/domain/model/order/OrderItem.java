package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {

    private final String productId;
    private final int quantity;
    private final Money unitPrice;

    public OrderItem(String productId, int quantity, Money unitPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        this.productId = Objects.requireNonNull(productId);
        this.quantity = quantity;
        this.unitPrice = Objects.requireNonNull(unitPrice);
    }

    public String productId() { return productId; }
    public int quantity() { return quantity; }
    public Money getUnitPrice() { return unitPrice; }

    public Money subtotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}
