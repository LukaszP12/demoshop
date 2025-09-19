package main.java.com.example.demoshop.java.com.example.demoshop.domain.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    void saveAll(List<Order> orders);
    long count();
    Optional<Order> findById(String id);
    List<Order> findAll();

    List<Order> findByUserId(String customerId);
}
