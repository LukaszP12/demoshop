package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.ReviewCreatedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private String productId;   // reference to Product
    private String userId;      // reference to User
    private int rating;         // e.g., 1 to 5
    private String comment;
    private Instant createdAt;

    public Review(String productId, String userId, int rating, String comment) {
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = Instant.now();
    }

    public static ReviewCreatedEvent create(String productId, String userId, int rating, String comment) {
        Review review = new Review(productId, userId, rating, comment);
        return new ReviewCreatedEvent(review);
    }

    // getters
    public String getId() { return id; }
    public String getProductId() { return productId; }
    public String getUserId() { return userId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public Instant getCreatedAt() { return createdAt; }

    // setters (optional, if you need to update the comment/rating)

    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
