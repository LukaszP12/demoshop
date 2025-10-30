package example.demoshop.domain.event;


import example.demoshop.domain.model.common.Money;

public record PaymentSuccessfulEvent(String orderId, Money orderTotal) {}
