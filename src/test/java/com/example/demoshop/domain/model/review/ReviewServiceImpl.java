
import main.java.com.example.demoshop.java.com.example.demoshop.application.catalogue.exceptions.ReviewNotFoundException;

import org.springframework.stereotype.Service;


//@Service
//public class ReviewServiceImpl implements ReviewService {
//
//    private final ReviewRepository reviewRepository;
//
//    public ReviewServiceImpl(ReviewRepository reviewRepository) {
//        this.reviewRepository = reviewRepository;
//    }
//
////    @Override
////    public Review editReview(String reviewId, String newText, int newRating, String userId) {
////        // 1️⃣ Find review
////        Review review = reviewRepository.findById(reviewId)
////                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + reviewId));
////
////        if (!review.getUserId().equals(userId)) {
////            throw new IllegalStateException("User not authorized to edit this review");
////        }
////
////        // 3️⃣ Update fields
////        review.setText(newText);
////        review.setRating(newRating);
////
////        // 4️⃣ Save updated review
////        return reviewRepository.save(review);
////    }
//}
