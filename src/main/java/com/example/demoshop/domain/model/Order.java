package com.example.demoshop.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private final OrderId id;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.CREATED;

    public Order(OrderId id) {
        this.id = Objects.requireNonNull(id);
    }

    public OrderId id() { return id; }

    public List<OrderItem> items() { return Collections.unmodifiableList(items); }

    public OrderStatus status() { return status; }

    public void addItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Item is required");
        if (item.quantity() <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        items.add(item);
    }

    public Money total() {
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(Money.zero(), Money::add);
    }

    public void place() {
        if (items.isEmpty()) throw new IllegalStateException("Cannot place order without items");
        status = OrderStatus.PLACED;
    }

    public enum OrderStatus { CREATED, PLACED }

    public static class OrderId {
        private final String value;
        public OrderId(String value) { this.value = value; }
        public static OrderId newId() { return new OrderId(UUID.randomUUID().toString()); }
        public String value() { return value; }
    }
}

