package id.ac.ui.cs.advprog.review.repository;

import id.ac.ui.cs.advprog.review.model.Review;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ReviewRepository {
    private final List<Review> reviewData = new ArrayList<>();

    public Review save(Review review) {
        int index = -1;
        for (int i = 0; i < reviewData.size(); i++) {
            if (reviewData.get(i).getId().equals(review.getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            reviewData.remove(index);
            reviewData.add(index, review);
        } else {
            reviewData.add(review);
        }

        return review;
    }

    public Review findById(UUID id) {
        for (Review review : reviewData) {
            if (review.getId().equals(id)) {
                return review;
            }
        }
        return null;
    }

    public List<Review> findAll() {
        return new ArrayList<>(reviewData);
    }

    public List<Review> findByTechnicianId(UUID technicianId) {
        return reviewData.stream()
                .filter(review -> review.getTechnicianId().equals(technicianId))
                .collect(Collectors.toList());
    }

    public List<Review> findByUserId(UUID userId) {
        return reviewData.stream()
                .filter(review -> review.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void delete(UUID id) {
        reviewData.removeIf(review -> review.getId().equals(id));
    }
}