package example.demoshop.domain.repository;

import example.demoshop.domain.model.payment.Payment;
import example.demoshop.domain.model.payment.PaymentId;

import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(PaymentId id);
}
