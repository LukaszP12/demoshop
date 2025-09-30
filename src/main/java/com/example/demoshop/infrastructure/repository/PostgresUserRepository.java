package main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.User;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.UserRepository;
import main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgresUserRepository extends UserRepository, JpaRepository<UserEntity,Long> {
    Optional<User> findByEmail(String email);
}
