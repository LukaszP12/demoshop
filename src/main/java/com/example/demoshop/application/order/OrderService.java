package main.java.com.example.demoshop.java.com.example.demoshop.application.order;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<Order> listOrders(String userId, Order.OrderStatus status, Pageable pageable);
}
