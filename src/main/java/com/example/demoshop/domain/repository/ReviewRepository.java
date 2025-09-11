package main.java.com.example.demoshop.java.com.example.demoshop.domain.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    // Find all reviews for a given product
    List<Review> findByProductId(String productId);

    // Find all reviews by a specific user
    List<Review> findByUserId(String userId);

    // Optional: find reviews for a product sorted by creation date
    List<Review> findByProductIdOrderByCreatedAtDesc(String productId);
}
