package example.demoshop.infrastructure.repository;

import example.demoshop.domain.model.user.User;
import example.demoshop.domain.repository.UserRepository;
import example.demoshop.infrastructure.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgresUserRepository extends UserRepository, JpaRepository<UserEntity,Long> {
    Optional<User> findByEmail(String email);
}
