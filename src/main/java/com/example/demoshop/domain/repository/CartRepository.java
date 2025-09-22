package main.java.com.example.demoshop.java.com.example.demoshop.domain.repository;


import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.Cart;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.CartId;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    void save(Cart cart);                    // Add or update a cart
    Optional<Cart> findByUserId(String userId);      // Find by cart id
    List<Cart> findAll();                     // Retrieve all carts
//    void delete(CartId id);                   // Remove cart by id

    void clearCart(String userId);
}
