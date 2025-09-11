package main.java.com.example.demoshop.java.com.example.demoshop.application.catalogue;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.ReviewCreatedEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Review;
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
}
