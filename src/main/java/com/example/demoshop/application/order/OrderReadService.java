package com.example.demoshop.application.order;

import com.example.demoshop.domain.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderReadService {
    Page<Order> listOrders(String userId, Order.OrderStatus status, Pageable pageable);

    Order getOrderById(String orderId);
}
