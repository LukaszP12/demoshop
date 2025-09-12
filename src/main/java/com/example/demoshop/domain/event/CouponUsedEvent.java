package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import java.time.Instant;

public class CouponUsedEvent {
    private final String couponCode;
    private final String orderId;
    private final Instant usedAt;

    public CouponUsedEvent(String couponCode, String orderId) {
        this.couponCode = couponCode;
        this.orderId = orderId;
        this.usedAt = Instant.now();
    }

    public String couponCode() { return couponCode; }
    public String orderId() { return orderId; }
    public Instant usedAt() { return usedAt; }
}
