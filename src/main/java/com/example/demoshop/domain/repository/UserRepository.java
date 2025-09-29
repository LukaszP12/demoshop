package main.java.com.example.demoshop.java.com.example.demoshop.domain.repository;


import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.User;

import java.util.Optional;

public interface UserRepository {

    // Save or update a user
    User save(User user);

    // Find a user by ID
    Optional<User> findById(String id);

    // Optional: find by email
    Optional<User> findByEmail(String email);
}
