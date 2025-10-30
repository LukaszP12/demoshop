package example.demoshop.domain.event;

import example.demoshop.domain.model.payment.PaymentId;

public record PaymentFailedEvent(PaymentId paymentId, String orderId, String reason) {}