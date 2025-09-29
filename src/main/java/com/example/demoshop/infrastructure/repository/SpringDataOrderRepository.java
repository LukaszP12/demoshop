package main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataOrderRepository extends MongoRepository<Order,String> {
    List<Order> findByUserId(String userId);

    // Find orders by userId with pagination
    Page<Order> findByUserId(String userId, Pageable pageable);

    // Find orders by status with pagination
    Page<Order> findByStatus(Order.OrderStatus status,Pageable pageable);

    // Find orders by userId and status
    Page<Order> findByUserIdAndStatus(String userId,Order.OrderStatus status,Pageable pageable);
}
