package com.example.demoshop.infrastructure.repository;

import com.example.demoshop.domain.model.cart.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataCartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserId(String userId);
}
