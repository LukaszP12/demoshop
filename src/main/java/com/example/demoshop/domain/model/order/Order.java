package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.PaymentSuccessfulEvent;
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
    private List<OrderItem> items;
    private BigDecimal total; // ✅ total as BigDecimal
    private String currency;
    private Coupon coupon;    // optional applied coupon
    private Instant createdAt;
    private String status;    // e.g., PENDING, SHIPPED, CANCELLED

    public Order(String userId, List<OrderItem> items, BigDecimal total, String currency, Coupon coupon) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.coupon = coupon;
        this.currency = currency;
        this.createdAt = Instant.now();
        this.status = "PENDING";
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<OrderItem> getItems() {
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

    public String getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Business methods

    public void cancel() {
        if ("SHIPPED".equals(status)) {
            throw new IllegalStateException("Cannot cancel a shipped order");
        }
        this.status = "CANCELLED";
    }

    public void markAsShipped() {
        if ("CANCELLED".equals(status)) {
            throw new IllegalStateException("Cannot ship a cancelled order");
        }
        this.status = "SHIPPED";
    }

    public void markPaid() {
        if (!"PENDING".equals(status)) {
            throw new IllegalStateException("Order can only be marked as PAID from PENDING state");
        }
        this.status = "PAID";
    }

    public void markShipped() {
        if (!"PAID".equals(status)) {
            throw new IllegalStateException("Order can only be marked as SHIPPED from PAID state");
        }
        this.status = "SHIPPED";
    }

    public void markDelivered() {
        if (!"SHIPPED".equals(status)) {
            throw new IllegalStateException("Order can only be marked as DELIVERED from SHIPPED state");
        }
        this.status = "DELIVERED";
    }

    public void markReturned() {
        if (!"DELIVERED".equals(status)) {
            throw new IllegalStateException("Order can only be marked as RETURNED from DELIVERED state");
        }
        this.status = "RETURNED";
    }
}
