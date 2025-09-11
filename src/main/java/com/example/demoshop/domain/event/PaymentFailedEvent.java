import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.payment.PaymentId;

public record PaymentFailedEvent(PaymentId paymentId, String orderId, String reason) {}
