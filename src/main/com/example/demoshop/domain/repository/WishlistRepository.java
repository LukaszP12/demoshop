package example.demoshop.domain.repository;

import example.demoshop.domain.model.user.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishlistRepository extends MongoRepository<Wishlist,String> {
    Optional<Wishlist> findByUserId(String userId);
}
