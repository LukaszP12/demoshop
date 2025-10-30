package example.demoshop.domain.event;

import example.demoshop.domain.model.catalogue.Review;
import java.time.Instant;

public class ReviewCreatedEvent {

    private final String reviewId;
    private final String productId;
    private final String userId;
    private final int rating;
    private final Instant createdAt;

    public ReviewCreatedEvent(Review review) {
        this.reviewId = review.getId();
        this.productId = review.getProductId();
        this.userId = review.getUserId();
        this.rating = review.getRating();
        this.createdAt = review.getCreatedAt();
    }

    public String getReviewId() { return reviewId; }
    public String getProductId() { return productId; }
    public String getUserId() { return userId; }
    public int getRating() { return rating; }
    public Instant getCreatedAt() { return createdAt; }
}
