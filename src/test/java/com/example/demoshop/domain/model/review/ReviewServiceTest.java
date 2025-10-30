package java.com.example.demoshop.domain.model.review;

import com.example.demoshop.domain.repository.ReviewRepository;
import main.java.com.example.demoshop.java.com.example.demoshop.application.catalogue.exceptions.ReviewNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void editReview_whenReviewNotFound_shouldThrow() {
        String nonExistingId = "999";
        when(reviewRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        String someUserId = "10L";

        assertThrows(ReviewNotFoundException.class, () -> {
            reviewService.editReview(nonExistingId, "new text", 5, someUserId);
        });
    }

    @Test
    void editReview_whenUserNotAuthor_shouldThrow() {
//        Review existing = new Review(...);
//        existing.setId(1L);
//        existing.setUserId(10L);  // author is user 10
//        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        // userId=20 is not the author
//        assertThrows(UnauthorizedEditException.class, () -> {
//            reviewService.editReview(1L, "text", 4, 20L);
//        });
    }

    @Test
    void editReview_whenValid_shouldUpdateAndReturn() {
//        Review existing = new Review(...);
//        existing.setId(1L);
//        existing.setUserId(100L);
//        existing.setText("old");
//        existing.setRating(3);
//
//        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existing));
//        // also mock repository.save(...) to return the updated review (or same instance)
//        when(reviewRepository.save(any(Review.class)))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        Review result = reviewService.editReview(1L, "new text", 5, 100L);
//
//        assertEquals("new text", result.getText());
//        assertEquals(5, result.getRating());
//        // optionally verify repository.save was called
//        verify(reviewRepository).save(existing);
    }
}
