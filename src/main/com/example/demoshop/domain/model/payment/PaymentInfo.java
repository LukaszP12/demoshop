package example.demoshop.domain.model.payment;

import example.demoshop.domain.model.order.Order;

record PaymentInfo(Order order, String paymentMethod) {
}
