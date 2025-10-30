package example.demoshop.domain.model.catalogue;

import java.time.Instant;

public class ProductReview {

    private final String customerId;
    private final String comment;
    private final int rating; // 1-5
    private final Instant createdAt;

    public ProductReview(String customerId, String comment, int rating) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be 1-5");
        this.customerId = customerId;
        this.comment = comment;
        this.rating = rating;
        this.createdAt = Instant.now();
    }

    public String customerId() { return customerId; }
    public String comment() { return comment; }
    public int rating() { return rating; }
    public Instant createdAt() { return createdAt; }
}
