package id.ac.ui.cs.advprog.review.service;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review createReview(ReviewDTO reviewDTO) {
        UUID reviewId = reviewDTO.getId() != null ? reviewDTO.getId() : UUID.randomUUID();

        Review newReview = Review.builder()
                .id(reviewId)
                .userId(reviewDTO.getUserId())
                .technicianId(reviewDTO.getTechnicianId())
                .comment(reviewDTO.getComment())
                .rating(reviewDTO.getRating())
                .build();

        return reviewRepository.save(newReview);
    }

    @Override
    public Review getReviewById(UUID id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByTechnicianId(UUID technicianId) {
        return reviewRepository.findByTechnicianId(technicianId);
    }

    @Override
    public List<Review> getReviewsByUserId(UUID userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public Review updateReview(UUID id, ReviewDTO reviewDTO) {
        Optional<Review> existingReviewOpt = reviewRepository.findById(id);

        if (existingReviewOpt.isEmpty()) {
            return null;
        }

        Review existingReview = existingReviewOpt.get();

        Review updatedReview = Review.builder()
                .id(id)
                .userId(existingReview.getUserId())
                .technicianId(existingReview.getTechnicianId())
                .comment(reviewDTO.getComment())
                .rating(reviewDTO.getRating())
                .build();

        return reviewRepository.save(updatedReview);
    }

    @Override
    public boolean deleteReview(UUID id, UUID userId) {
        Optional<Review> existingReviewOpt = reviewRepository.findById(id);

        if (existingReviewOpt.isEmpty()) {
            return false;
        }

        Review existingReview = existingReviewOpt.get();

        // Check if the user is the owner of the review
        if (!existingReview.getUserId().equals(userId)) {
            return false;
        }

        reviewRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteReviewByAdmin(UUID id) {
        if (!reviewRepository.existsById(id)) {
            return false;
        }

        reviewRepository.deleteById(id);
        return true;
    }
}