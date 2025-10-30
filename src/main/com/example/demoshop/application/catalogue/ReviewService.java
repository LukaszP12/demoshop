package example.demoshop.application.catalogue;

import example.demoshop.application.catalogue.exceptions.ReviewNotFoundException;
import example.demoshop.application.catalogue.exceptions.UnauthorizedEditException;
import example.demoshop.domain.event.ReviewCreatedEvent;
import example.demoshop.domain.model.catalogue.Review;
import example.demoshop.domain.repository.ReviewRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ReviewService(ReviewRepository reviewRepository, ApplicationEventPublisher eventPublisher) {
        this.reviewRepository = reviewRepository;
        this.eventPublisher = eventPublisher;
    }

    public Review addReview(String productId, String userId, int rating, String comment) {
        Review review = new Review(productId, userId, rating, comment);
        reviewRepository.save(review);
        eventPublisher.publishEvent(new ReviewCreatedEvent(review));
        return review;
    }

    public Review editReview(String reviewId, String newText, int newRating, String userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found: " + reviewId));

        if (!review.getUserId().equals(userId)) {
            throw new UnauthorizedEditException("User " + userId + " not allowed to edit review " + reviewId);
        }

        review.setText(newText);
        review.setRating(newRating);

        return reviewRepository.save(review);
    }
}
