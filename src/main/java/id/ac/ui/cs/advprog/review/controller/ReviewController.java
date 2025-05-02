package id.ac.ui.cs.advprog.review.controller;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 1. Pengguna dapat memberikan ulasan dan rating terhadap teknisi (C)
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO reviewDTO) {
        Review createdReview = reviewService.createReview(reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    // 2. Semua pengguna dapat melihat ulasan dan rating teknisi (R)
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable UUID id) {
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<List<Review>> getReviewsByTechnicianId(@PathVariable UUID technicianId) {
        List<Review> reviews = reviewService.getReviewsByTechnicianId(technicianId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable UUID userId) {
        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // 3. Pengguna dapat mengubah ulasan mereka jika ada perubahan ulasan (U)
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable UUID id,
            @RequestBody ReviewDTO reviewDTO) {
        Review updatedReview = reviewService.updateReview(id, reviewDTO);
        if (updatedReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // 4. Pengguna & Admin dapat menghapus ulasan yang sudah tidak relevan (D)
    // Pengguna hanya dapat menghapus ulasan yang pernah mereka buat
    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable UUID id,
            @PathVariable UUID userId) {
        boolean deleted = reviewService.deleteReview(id, userId);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Admin dapat menghapus semua ulasan
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteReviewByAdmin(@PathVariable UUID id) {
        boolean deleted = reviewService.deleteReviewByAdmin(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}