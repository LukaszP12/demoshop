package com.example.demoshop.domain.repository;

import com.example.demoshop.domain.model.catalogue.Review;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByProductId(String productId);

    List<Review> findByUserId(String userId);

    List<Review> findByProductIdOrderByCreatedAtDesc(String productId);

    @Override
    <S extends Review> Optional<S> findOne(Example<S> example);

    Optional<Review> findById(String id);
}
