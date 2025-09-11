package com.example.demoshop.domain.repository;

import com.example.demoshop.domain.model.cart.Cart;
import com.example.demoshop.domain.model.cart.CartId;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    void save(Cart cart);                    // Add or update a cart
    Optional<Cart> findById(CartId id);      // Find by cart id
    List<Cart> findAll();                     // Retrieve all carts
    void delete(CartId id);                   // Remove cart by id
}
