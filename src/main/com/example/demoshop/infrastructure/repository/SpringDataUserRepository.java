package example.demoshop.infrastructure.repository;

import example.demoshop.domain.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends MongoRepository<User, User.UserId> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
