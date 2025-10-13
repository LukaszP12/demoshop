package main.java.com.example.demoshop.domain;

import main.java.com.example.demoshop.java.com.example.demoshop.application.catalogue.exceptions.ReviewNotFoundException;
import main.java.com.example.demoshop.java.com.example.demoshop.application.catalogue.exceptions.UnauthorizedEditException;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Review;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewServiceTest {

    @Test
    void editReview_whenReviewNotFound_shouldThrow() {
        Long nonExistingId = 999L;
        when(reviewRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> {
            reviewService.editReview(nonExistingId, "new text", 5, someUserId);
        });
    }

    @Test
    void editReview_whenUserNotAuthor_shouldThrow() {
        Review existing = new Review(...);
        existing.setId(1L);
        existing.setUserId(10L);  // author is user 10
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existing));

        // userId=20 is not the author
        assertThrows(UnauthorizedEditException.class, () -> {
            reviewService.editReview(1L, "text", 4, 20L);
        });
    }

    @Test
    void editReview_whenValid_shouldUpdateAndReturn() {
        Review existing = new Review(...);
        existing.setId(1L);
        existing.setUserId(100L);
        existing.setText("old");
        existing.setRating(3);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existing));
        // also mock repository.save(...) to return the updated review (or same instance)
        when(reviewRepository.save(any(Review.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Review result = reviewService.editReview(1L, "new text", 5, 100L);

        assertEquals("new text", result.getText());
        assertEquals(5, result.getRating());
        // optionally verify repository.save was called
        verify(reviewRepository).save(existing);
    }

}
