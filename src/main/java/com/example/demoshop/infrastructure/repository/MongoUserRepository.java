package com.example.demoshop.infrastructure.repository;

import com.example.demoshop.domain.model.user.User;
import com.example.demoshop.domain.repository.UserRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MongoUserRepository implements UserRepository {

    private final SpringDataUserRepository repository;

    public MongoUserRepository(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        repository.save(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(new User.UserId(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
