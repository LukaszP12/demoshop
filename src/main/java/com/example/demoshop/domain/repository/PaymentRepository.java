package main.java.com.example.demoshop.java.com.example.demoshop.domain.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.payment.Payment;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.payment.PaymentId;

import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(PaymentId id);
}
