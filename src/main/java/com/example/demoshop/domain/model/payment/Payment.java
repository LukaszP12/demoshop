package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.payment;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;
import java.time.Instant;

public class Payment {
    private final PaymentId id;
    private final String orderId;
    private final PaymentMethod method;
    private final Money amount;
    private boolean successful;
    private final Instant createdAt;

    public Payment(String orderId, PaymentMethod method, Money amount) {
        this.id = PaymentId.newId();
        this.orderId = orderId;
        this.method = method;
        this.amount = amount;
        this.successful = false;
        this.createdAt = Instant.now();
    }

    public void markSuccessful() {
        this.successful = true;
    }

    public void markFailed() {
        this.successful = false;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public PaymentId id() { return id; }
    public String orderId() { return orderId; }
    public PaymentMethod method() { return method; }
    public Money amount() { return amount; }
    public Instant createdAt() { return createdAt; }
}
