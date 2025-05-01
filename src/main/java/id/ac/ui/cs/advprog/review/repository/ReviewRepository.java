package id.ac.ui.cs.advprog.review.repository;

import id.ac.ui.cs.advprog.review.model.Review;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ReviewRepository {
    private final List<Review> reviewData;

    public Review save(Review review) {}

    public Review findById(UUID id) {}

    public List<Review> findAll() {}

    public List<Review> findByTechnicianId(UUID technicianId) {}

    public List<Review> findByUserId(UUID userId) {}

    public void delete(UUID id) {}
}