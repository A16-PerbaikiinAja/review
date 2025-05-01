package id.ac.ui.cs.advprog.review.service;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {}

    public Review createReview(ReviewDTO reviewDTO) {}

    public Review getReviewById(UUID id) {}

    public List<Review> getAllReviews() {}

    public List<Review> getReviewsByTechnicianId(UUID technicianId) {}

    public List<Review> getReviewsByUserId(UUID userId) {}

    public Review updateReview(UUID id, ReviewDTO reviewDTO) {}

    public boolean deleteReview(UUID id, UUID userId) {}

    public boolean deleteReviewByAdmin(UUID id) {}
}