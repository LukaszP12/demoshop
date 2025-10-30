package com.example.demoshop.domain.event;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;

public record PaymentSuccessfulEvent(String orderId, Money orderTotal) {}
