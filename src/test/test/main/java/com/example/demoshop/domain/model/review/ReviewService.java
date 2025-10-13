package main.java.com.example.demoshop.domain.model.review;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Review;

public interface ReviewService {
    Review editReview(String reviewId, String newText, int newRating, String userId);
}
