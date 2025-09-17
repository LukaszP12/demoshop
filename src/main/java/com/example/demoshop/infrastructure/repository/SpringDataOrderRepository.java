package main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataOrderRepository extends MongoRepository<Order,String> {
}
