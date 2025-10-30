package com.example.demoshop.domain.model.payment;

import com.example.demoshop.domain.model.order.Order;

record PaymentInfo(Order order, String paymentMethod) {
}
