package id.ac.ui.cs.advprog.review.model;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewTest {
    private UUID reviewId;
    private UUID userId;
    private UUID technicianId;

    @BeforeEach
    void setUp() {
        reviewId = UUID.randomUUID();
        userId = UUID.randomUUID();
        technicianId = UUID.randomUUID();
    }

    @Test
    void testCreateValidReview() {
        String comment = "Pengerjaannya sangat oke!";
        int rating = 5;

        Review review = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment(comment)
                .rating(rating)
                .build();

        assertEquals(reviewId, review.getId());
        assertEquals(userId, review.getUserId());
        assertEquals(technicianId, review.getTechnicianId());
        assertEquals(comment, review.getComment());
        assertEquals(rating, review.getRating());
        assertNotNull(review.getCreatedAt());
    }

    @Test
    void testIdIsMissing() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Review.builder()
                    // .id(null)
                    .userId(userId)
                    .technicianId(technicianId)
                    .comment("Pengerjaannya sangat oke!")
                    .rating(5)
                    .build();
        });

        assertEquals("Review id is required", exception.getMessage());
    }

    @Test
    void testUserIdIsMissing() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Review.builder()
                    .id(reviewId)
                    // .userId(null)
                    .technicianId(technicianId)
                    .comment("Pengerjaannya sangat oke!")
                    .rating(5)
                    .build();
        });

        assertEquals("User id is required", exception.getMessage());
    }

    @Test
    void testTechnicianIdIsMissing() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Review.builder()
                    .id(reviewId)
                    .userId(userId)
                    // .technicianId(null)
                    .comment("Pengerjaannya sangat oke!")
                    .rating(5)
                    .build();
        });

        assertEquals("Technician id is required", exception.getMessage());
    }

    @Test
    void testCommentIsMissing() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Review.builder()
                    .id(reviewId)
                    .userId(userId)
                    .technicianId(technicianId)
                    // .comment(null)
                    .rating(5)
                    .build();
        });

        assertEquals("Comment is required", exception.getMessage());
    }

    @Test
    void testCommentIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Review.builder()
                    .id(reviewId)
                    .userId(userId)
                    .technicianId(technicianId)
                    .comment("") // Empty
                    .rating(5)
                    .build();
        });

        assertEquals("Comment is required", exception.getMessage());
    }

    @Test
    void testRatingLowerBoundary() {
        Review review = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Pengerjaannya sangat oke!")
                .rating(1)
                .build();

        assertEquals(1, review.getRating());
    }

    @Test
    void testRatingUpperBoundary() {
        Review review = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Pengerjaannya sangat oke!")
                .rating(5)
                .build();

        assertEquals(5, review.getRating());
    }

    @Test
    void testRatingIsInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Review.builder()
                    .id(reviewId)
                    .userId(userId)
                    .technicianId(technicianId)
                    .comment("Pengerjaannya sangat oke!")
                    .rating(-1) // Invalid
                    .build();
        });

        assertEquals("Rating must be between 1 and 5", exception.getMessage());
    }

    @Test
    void testCreatedAt() {
        Review review = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Pengerjaannya sangat oke!")
                .rating(5)
                .build();

        assertNotNull(review.getCreatedAt());
    }
}