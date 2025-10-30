package java.com.example.demoshop.domain.model.review;

import com.example.demoshop.domain.model.catalogue.Review;

public interface ReviewService {
    Review editReview(String reviewId, String newText, int newRating, String userId);
}
