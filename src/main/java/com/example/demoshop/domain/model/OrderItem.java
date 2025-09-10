package com.example.demoshop.domain.model;

import java.util.Objects;

public class OrderItem {

    private final String productId;
    private final int quantity;
    private final Money price;

    public OrderItem(String productId, int quantity, Money price) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        this.productId = Objects.requireNonNull(productId);
        this.quantity = quantity;
        this.price = Objects.requireNonNull(price);
    }

    public String productId() { return productId; }
    public int quantity() { return quantity; }
    public Money price() { return price; }

    public Money subtotal() { return price.multiply(quantity); }
}
