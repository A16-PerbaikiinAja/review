package id.ac.ui.cs.advprog.review.service;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.model.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    Review createReview(ReviewDTO reviewDTO);
    Review getReviewById(UUID id);
    List<Review> getAllReviews();
    List<Review> getReviewsByTechnicianId(UUID technicianId);
    List<Review> getReviewsByUserId(UUID userId);
    Review updateReview(UUID id, ReviewDTO reviewDTO);
    boolean deleteReview(UUID id, UUID userId);
    boolean deleteReviewByAdmin(UUID id);
}