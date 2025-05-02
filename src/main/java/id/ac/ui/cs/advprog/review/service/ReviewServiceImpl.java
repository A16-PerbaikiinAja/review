package id.ac.ui.cs.advprog.review.service;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return reviewRepository.findById(id);
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
        Review existingReview = reviewRepository.findById(id);

        if (existingReview == null) {
            return null;
        }

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
        Review existingReview = reviewRepository.findById(id);

        if (existingReview == null) {
            return false;
        }

        // Check if the user is the owner of the review
        if (!existingReview.getUserId().equals(userId)) {
            return false;
        }

        reviewRepository.delete(id);
        return true;
    }

    @Override
    public boolean deleteReviewByAdmin(UUID id) {
        Review existingReview = reviewRepository.findById(id);

        if (existingReview == null) {
            return false;
        }

        reviewRepository.delete(id);
        return true;
    }
}