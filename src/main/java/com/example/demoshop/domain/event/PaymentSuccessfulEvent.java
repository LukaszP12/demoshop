package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;

public record PaymentSuccessfulEvent(Order.OrderId orderId, Money orderTotal) {}
