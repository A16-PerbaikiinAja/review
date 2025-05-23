package id.ac.ui.cs.advprog.review.controller;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.dto.ReviewResponseDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    private UUID extractUserId(Authentication auth) {
        Object principal = auth.getPrincipal();
        if (principal instanceof String) {
            return UUID.fromString((String) principal);
        } else if (principal instanceof User) {
            return UUID.fromString(((User) principal).getUsername());
        } else {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        List<ReviewResponseDTO> reviews = reviewService.getAllReviewResponses();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByTechnicianId(@PathVariable UUID technicianId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewResponsesByTechnicianId(technicianId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ReviewResponseDTO>> getUserReviews(Authentication auth) {
        UUID userId = extractUserId(auth);
        List<ReviewResponseDTO> reviews = reviewService.getReviewResponsesByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable UUID reviewId) {
        ReviewResponseDTO review = reviewService.getReviewResponseById(reviewId);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO reviewDTO, Authentication auth) {
        UUID userId = extractUserId(auth);
        reviewDTO.setUserId(userId);

        Review createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(createdReview);
    }

    @PutMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateReview(
            @PathVariable UUID reviewId,
            @RequestBody ReviewDTO reviewDTO,
            Authentication auth) {

        UUID userId = extractUserId(auth);
        Review existingReview = reviewService.getReviewById(reviewId);

        if (existingReview == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existingReview.getUserId().equals(userId)) {
            return ResponseEntity.badRequest().body("You can only update your own reviews");
        }

        Review updatedReview = reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> deleteReview(@PathVariable UUID reviewId, Authentication auth) {
        UUID userId = extractUserId(auth);
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean result;
        try {
            if (isAdmin) {
                result = reviewService.deleteReviewByAdmin(reviewId);
            } else {
                result = reviewService.deleteReview(reviewId, userId);
            }

            if (result) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("Review not found or you don't have permission to delete it");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}