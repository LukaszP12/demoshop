package com.example.demoshop.application;

import com.example.demoshop.domain.model.order.Order;
import com.example.demoshop.domain.model.user.User;
import com.example.demoshop.domain.repository.OrderRepository;
import com.example.demoshop.domain.repository.UserRepository;

public class PlaceOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PlaceOrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order placeOrder(String userId, Order order) {
        User user = userRepository.findById(new User.UserId(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        order.place(); // domain logic
        return orderRepository.save(order);
    }
}
