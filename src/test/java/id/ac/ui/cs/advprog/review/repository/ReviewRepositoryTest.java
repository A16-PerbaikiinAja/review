package id.ac.ui.cs.advprog.review.repository;

import id.ac.ui.cs.advprog.review.model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void testSaveAndFindById() {
        // Create a review
        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID technicianId = UUID.randomUUID();

        Review review = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Pengerjaannya sangat oke!")
                .rating(5)
                .build();

        // Save the review
        entityManager.persist(review);
        entityManager.flush();

        // Find the review by ID
        Optional<Review> found = reviewRepository.findById(reviewId);

        // Assertions
        assertTrue(found.isPresent());
        assertEquals(reviewId, found.get().getId());
        assertEquals(userId, found.get().getUserId());
        assertEquals(technicianId, found.get().getTechnicianId());
        assertEquals("Pengerjaannya sangat oke!", found.get().getComment());
        assertEquals(5, found.get().getRating());
    }

    @Test
    void testSaveAndUpdate() {
        // Create a review
        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID technicianId = UUID.randomUUID();

        Review review = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Original comment")
                .rating(4)
                .build();

        // Save the review
        entityManager.persist(review);
        entityManager.flush();

        // Update the review
        Review updatedReview = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Updated comment")
                .rating(3)
                .build();

        Review result = reviewRepository.save(updatedReview);
        entityManager.flush();

        // Find the review again to check updates
        Optional<Review> found = reviewRepository.findById(reviewId);

        // Assertions
        assertTrue(found.isPresent());
        assertEquals("Updated comment", found.get().getComment());
        assertEquals(3, found.get().getRating());
    }

    @Test
    void testFindAll() {
        // Create reviews
        UUID technicianId = UUID.randomUUID();

        Review review1 = Review.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .technicianId(technicianId)
                .comment("Comment 1")
                .rating(5)
                .build();

        Review review2 = Review.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .technicianId(technicianId)
                .comment("Comment 2")
                .rating(4)
                .build();

        // Save reviews
        entityManager.persist(review1);
        entityManager.persist(review2);
        entityManager.flush();

        // Find all reviews
        List<Review> allReviews = reviewRepository.findAll();

        // Assertions
        assertTrue(allReviews.size() >= 2);
        assertTrue(allReviews.stream().anyMatch(r -> r.getId().equals(review1.getId())));
        assertTrue(allReviews.stream().anyMatch(r -> r.getId().equals(review2.getId())));
    }

    @Test
    void testFindByTechnicianId() {
        UUID technicianId = UUID.randomUUID();

        // Create two reviews for the same technician
        Review review1 = Review.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .technicianId(technicianId)
                .comment("Technician review 1")
                .rating(5)
                .build();

        Review review2 = Review.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .technicianId(technicianId)
                .comment("Technician review 2")
                .rating(4)
                .build();

        // Create one review for a different technician
        Review review3 = Review.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .technicianId(UUID.randomUUID())
                .comment("Different technician review")
                .rating(3)
                .build();

        // Save all reviews
        entityManager.persist(review1);
        entityManager.persist(review2);
        entityManager.persist(review3);
        entityManager.flush();

        // Find reviews by technician ID
        List<Review> technicianReviews = reviewRepository.findByTechnicianId(technicianId);

        // Assertions
        assertEquals(2, technicianReviews.size());
        assertTrue(technicianReviews.stream().anyMatch(r -> r.getId().equals(review1.getId())));
        assertTrue(technicianReviews.stream().anyMatch(r -> r.getId().equals(review2.getId())));
    }

    @Test
    void testFindByUserId() {
        UUID userId = UUID.randomUUID();

        // Create two reviews by the same user
        Review review1 = Review.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .technicianId(UUID.randomUUID())
                .comment("User review 1")
                .rating(5)
                .build();

        Review review2 = Review.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .technicianId(UUID.randomUUID())
                .comment("User review 2")
                .rating(4)
                .build();

        // Create one review by a different user
        Review review3 = Review.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .technicianId(UUID.randomUUID())
                .comment("Different user review")
                .rating(3)
                .build();

        // Save all reviews
        entityManager.persist(review1);
        entityManager.persist(review2);
        entityManager.persist(review3);
        entityManager.flush();

        // Find reviews by user ID
        List<Review> userReviews = reviewRepository.findByUserId(userId);

        // Assertions
        assertEquals(2, userReviews.size());
        assertTrue(userReviews.stream().anyMatch(r -> r.getId().equals(review1.getId())));
        assertTrue(userReviews.stream().anyMatch(r -> r.getId().equals(review2.getId())));
    }

    @Test
    void testDelete() {
        UUID reviewId = UUID.randomUUID();

        Review review = Review.builder()
                .id(reviewId)
                .userId(UUID.randomUUID())
                .technicianId(UUID.randomUUID())
                .comment("Delete test")
                .rating(5)
                .build();

        // Save the review
        entityManager.persist(review);
        entityManager.flush();

        // Confirm review exists
        assertTrue(reviewRepository.findById(reviewId).isPresent());

        // Delete the review
        reviewRepository.deleteById(reviewId);
        entityManager.flush();

        // Confirm review is deleted
        assertFalse(reviewRepository.findById(reviewId).isPresent());
    }
}