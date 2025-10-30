package com.example.demoshop.application.shipping;

import com.example.demoshop.domain.model.order.Order;

public interface ShippingPort {
    void shipOrder(Order order);
}
