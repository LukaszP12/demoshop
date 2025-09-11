package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.payment.PaymentId;

public record PaymentSuccessfulEvent(PaymentId paymentId, String orderId) {}
