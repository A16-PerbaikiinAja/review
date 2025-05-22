package id.ac.ui.cs.advprog.review.service;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.repository.ReviewRepository;
import id.ac.ui.cs.advprog.review.repository.TechnicianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    private ReviewService reviewService;

    private UUID reviewId;
    private UUID userId;
    private UUID technicianId;
    private Review testReview;
    private ReviewDTO testReviewDTO;
    private LocalDateTime testCreatedAt;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewServiceImpl(reviewRepository, technicianRepository);

        reviewId = UUID.randomUUID();
        userId = UUID.randomUUID();
        technicianId = UUID.randomUUID();
        testCreatedAt = LocalDateTime.now().minusDays(1);

        testReview = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Pengerjaannya sangat oke!")
                .rating(5)
                .createdAt(testCreatedAt)
                .build();

        testReviewDTO = new ReviewDTO();
        testReviewDTO.setId(reviewId);
        testReviewDTO.setUserId(userId);
        testReviewDTO.setTechnicianId(technicianId);
        testReviewDTO.setComment("Pengerjaannya sangat oke!");
        testReviewDTO.setRating(5);
    }

    @Test
    void testCreateReview() {
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        Review result = reviewService.createReview(testReviewDTO);

        verify(reviewRepository).save(any(Review.class));
        assertEquals(testReview.getId(), result.getId());
        assertEquals(testReview.getUserId(), result.getUserId());
        assertEquals(testReview.getTechnicianId(), result.getTechnicianId());
        assertEquals(testReview.getComment(), result.getComment());
        assertEquals(testReview.getRating(), result.getRating());
    }

    @Test
    void testGetReviewById() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(testReview));

        Review result = reviewService.getReviewById(reviewId);

        verify(reviewRepository).findById(reviewId);
        assertEquals(testReview, result);
    }

    @Test
    void testGetReviewByIdNotFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        Review result = reviewService.getReviewById(reviewId);

        verify(reviewRepository).findById(reviewId);
        assertNull(result);
    }

    @Test
    void testGetAllReviews() {
        UUID reviewId2 = UUID.randomUUID();
        Review review2 = Review.builder()
                .id(reviewId2)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Good job")
                .rating(4)
                .build();

        when(reviewRepository.findAll()).thenReturn(Arrays.asList(testReview, review2));

        List<Review> result = reviewService.getAllReviews();

        verify(reviewRepository).findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(testReview));
        assertTrue(result.contains(review2));
    }

    @Test
    void testGetReviewsByTechnicianId() {
        when(reviewRepository.findByTechnicianId(technicianId)).thenReturn(List.of(testReview));

        List<Review> result = reviewService.getReviewsByTechnicianId(technicianId);

        verify(reviewRepository).findByTechnicianId(technicianId);
        assertEquals(1, result.size());
        assertEquals(testReview, result.get(0));
    }

    @Test
    void testGetReviewsByUserId() {
        when(reviewRepository.findByUserId(userId)).thenReturn(List.of(testReview));

        List<Review> result = reviewService.getReviewsByUserId(userId);

        verify(reviewRepository).findByUserId(userId);
        assertEquals(1, result.size());
        assertEquals(testReview, result.get(0));
    }

    @Test
    void testUpdateReview() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(testReview));

        ReviewDTO updatedDTO = new ReviewDTO();
        updatedDTO.setId(reviewId);
        updatedDTO.setUserId(userId);
        updatedDTO.setTechnicianId(technicianId);
        updatedDTO.setComment("Updated comment");
        updatedDTO.setRating(4);

        when(reviewRepository.save(argThat(review ->
                review.getId().equals(reviewId) &&
                        review.getComment().equals("Updated comment") &&
                        review.getRating() == 4 &&
                        review.getCreatedAt().equals(testCreatedAt)
        ))).thenAnswer(invocation -> {
            Review updatedReview = invocation.getArgument(0);
            return updatedReview;
        });

        Review result = reviewService.updateReview(reviewId, updatedDTO);

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository).save(any(Review.class));

        assertEquals(reviewId, result.getId());
        assertEquals("Updated comment", result.getComment());
        assertEquals(4, result.getRating());
        assertEquals(testCreatedAt, result.getCreatedAt()); // Verify createdAt is preserved
    }

    @Test
    void testUpdateReviewNotFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        ReviewDTO updatedDTO = new ReviewDTO();
        updatedDTO.setComment("Updated comment");
        updatedDTO.setRating(4);

        Review result = reviewService.updateReview(reviewId, updatedDTO);

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository, never()).save(any(Review.class));

        assertNull(result);
    }

    @Test
    void testDeleteReview() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(testReview));

        boolean result = reviewService.deleteReview(reviewId, userId);

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository).deleteById(reviewId);

        assertTrue(result);
    }

    @Test
    void testDeleteReviewByDifferentUser() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(testReview));

        boolean result = reviewService.deleteReview(reviewId, UUID.randomUUID());

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository, never()).deleteById(any(UUID.class));

        assertFalse(result);
    }

    @Test
    void testDeleteReviewByAdmin() {
        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        boolean result = reviewService.deleteReviewByAdmin(reviewId);

        verify(reviewRepository).existsById(reviewId);
        verify(reviewRepository).deleteById(reviewId);

        assertTrue(result);
    }

    @Test
    void testDeleteReviewNotFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        boolean result = reviewService.deleteReview(reviewId, userId);

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository, never()).deleteById(any(UUID.class));

        assertFalse(result);
    }
}