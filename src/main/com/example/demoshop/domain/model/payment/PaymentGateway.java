package example.demoshop.domain.model.payment;

import example.demoshop.domain.model.order.Order;

public interface PaymentGateway {
    PaymentInfo charge(Order order, String paymentMethod);
}
