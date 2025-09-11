package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.payment;


import java.util.UUID;

public class PaymentId {
    private final String id;

    private PaymentId(String id) {
        this.id = id;
    }

    public static PaymentId newId() {
        return new PaymentId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }
}
