package com.example.demoshop.domain.repository;

import com.example.demoshop.domain.model.Order;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Order.OrderId id);
}
