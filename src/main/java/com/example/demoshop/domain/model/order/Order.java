package com.example.demoshop.domain.model.order;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.Coupon;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String userId;
    private List<main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem> items;
    private BigDecimal total; // ✅ total as BigDecimal
    private String currency;
    private Coupon coupon;    // optional applied coupon
    private Instant createdAt;
    private OrderStatus status;    // e.g., PENDING, SHIPPED, CANCELLED

    public Order(String userId, List<main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem> items, BigDecimal total, String currency, Coupon coupon) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.currency = currency;
        this.coupon = coupon;
        this.createdAt = Instant.now();
        this.status = OrderStatus.PENDING;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Business methods

    public void markPaid() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Order can only be marked as PAID from PENDING state");
        }
        this.status = OrderStatus.PAID;
    }

    public void markShipped() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Order can only be marked as SHIPPED from PAID state");
        }
        this.status = OrderStatus.SHIPPED;
    }

    public void markDelivered() {
        if (status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Order can only be marked as DELIVERED from SHIPPED state");
        }
        this.status = OrderStatus.DELIVERED;
    }

    public void markReturned() {
        if (status != OrderStatus.DELIVERED) {
            throw new IllegalStateException("Order can only be marked as RETURNED from DELIVERED state");
        }
        this.status = OrderStatus.RETURNED;
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED || status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel a shipped order");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void applyCoupon(Coupon coupon) {

    }

    public enum OrderStatus {
        PENDING,
        PAID,
        SHIPPED,
        DELIVERED,
        RETURNED,
        CANCELLED
    }
}
