package id.ac.ui.cs.advprog.review.controller;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private UUID reviewId;
    private UUID userId;
    private UUID technicianId;
    private Review review;
    private ReviewDTO reviewDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reviewId = UUID.randomUUID();
        userId = UUID.randomUUID();
        technicianId = UUID.randomUUID();

        // Create a sample review
        review = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Pengerjaannya sangat oke!")
                .rating(5)
                .build();

        // Create a sample reviewDTO
        reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(userId);
        reviewDTO.setTechnicianId(technicianId);
        reviewDTO.setComment("Pengerjaannya sangat oke!");
        reviewDTO.setRating(5);
    }

    @Test
    void testCreateReview() {
        when(reviewService.createReview(any(ReviewDTO.class))).thenReturn(review);

        ResponseEntity<Review> response = reviewController.createReview(reviewDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reviewId, response.getBody().getId());

        verify(reviewService, times(1)).createReview(any(ReviewDTO.class));
    }

    @Test
    void testGetAllReviews() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewService.getAllReviews()).thenReturn(reviews);

        ResponseEntity<List<Review>> response = reviewController.getAllReviews();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    void testGetReviewById() {
        when(reviewService.getReviewById(reviewId)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.getReviewById(reviewId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reviewId, response.getBody().getId());

        verify(reviewService, times(1)).getReviewById(reviewId);
    }

    @Test
    void testGetReviewByIdNotFound() {
        when(reviewService.getReviewById(reviewId)).thenReturn(null);

        ResponseEntity<Review> response = reviewController.getReviewById(reviewId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(reviewService, times(1)).getReviewById(reviewId);
    }

    @Test
    void testGetReviewsByTechnicianId() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewService.getReviewsByTechnicianId(technicianId)).thenReturn(reviews);

        ResponseEntity<List<Review>> response = reviewController.getReviewsByTechnicianId(technicianId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(reviewService, times(1)).getReviewsByTechnicianId(technicianId);
    }

    @Test
    void testGetReviewsByUserId() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewService.getReviewsByUserId(userId)).thenReturn(reviews);

        ResponseEntity<List<Review>> response = reviewController.getReviewsByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(reviewService, times(1)).getReviewsByUserId(userId);
    }

    @Test
    void testUpdateReview() {
        when(reviewService.updateReview(eq(reviewId), any(ReviewDTO.class))).thenReturn(review);

        ResponseEntity<Review> response = reviewController.updateReview(reviewId, reviewDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reviewId, response.getBody().getId());

        verify(reviewService, times(1)).updateReview(eq(reviewId), any(ReviewDTO.class));
    }

    @Test
    void testUpdateReviewNotFound() {
        when(reviewService.updateReview(eq(reviewId), any(ReviewDTO.class))).thenReturn(null);

        ResponseEntity<Review> response = reviewController.updateReview(reviewId, reviewDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(reviewService, times(1)).updateReview(eq(reviewId), any(ReviewDTO.class));
    }

    @Test
    void testDeleteReview() {
        when(reviewService.deleteReview(reviewId, userId)).thenReturn(true);

        ResponseEntity<Void> response = reviewController.deleteReview(reviewId, userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(reviewService, times(1)).deleteReview(reviewId, userId);
    }

    @Test
    void testDeleteReviewNotFound() {
        when(reviewService.deleteReview(reviewId, userId)).thenReturn(false);

        ResponseEntity<Void> response = reviewController.deleteReview(reviewId, userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(reviewService, times(1)).deleteReview(reviewId, userId);
    }

    @Test
    void testDeleteReviewByAdmin() {
        when(reviewService.deleteReviewByAdmin(reviewId)).thenReturn(true);

        ResponseEntity<Void> response = reviewController.deleteReviewByAdmin(reviewId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(reviewService, times(1)).deleteReviewByAdmin(reviewId);
    }

    @Test
    void testDeleteReviewByAdminNotFound() {
        when(reviewService.deleteReviewByAdmin(reviewId)).thenReturn(false);

        ResponseEntity<Void> response = reviewController.deleteReviewByAdmin(reviewId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(reviewService, times(1)).deleteReviewByAdmin(reviewId);
    }
}