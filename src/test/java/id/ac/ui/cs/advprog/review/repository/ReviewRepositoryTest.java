package id.ac.ui.cs.advprog.review.repository;

import id.ac.ui.cs.advprog.review.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewRepositoryTest {
    private ReviewRepository reviewRepository;
    private Review review1;
    private Review review2;
    private UUID reviewId1;
    private UUID reviewId2;
    private UUID userId1;
    private UUID userId2;
    private UUID technicianId;

    @BeforeEach
    void setUp() {
        reviewRepository = new ReviewRepository();

        reviewId1 = UUID.randomUUID();
        reviewId2 = UUID.randomUUID();
        userId1 = UUID.randomUUID();
        userId2 = UUID.randomUUID();
        technicianId = UUID.randomUUID();

        review1 = Review.builder()
                .id(reviewId1)
                .userId(userId1)
                .technicianId(technicianId)
                .comment("Pengerjaannya sangat oke!")
                .rating(5)
                .build();

        review2 = Review.builder()
                .id(reviewId2)
                .userId(userId2)
                .technicianId(technicianId)
                .comment("Bagus, tapi mungkin bisa lebih cepat lagi.")
                .rating(4)
                .build();
    }

    @Test
    void testSaveCreate() {
        Review savedReview = reviewRepository.save(review1);

        assertEquals(review1.getId(), savedReview.getId());
        assertEquals(review1.getUserId(), savedReview.getUserId());
        assertEquals(review1.getTechnicianId(), savedReview.getTechnicianId());
        assertEquals(review1.getComment(), savedReview.getComment());
        assertEquals(review1.getRating(), savedReview.getRating());
        assertEquals(review1.getCreatedAt(), savedReview.getCreatedAt());
    }

    @Test
    void testSaveUpdate() {
        reviewRepository.save(review1);

        Review updatedReview = Review.builder()
                .id(reviewId1)
                .userId(userId1)
                .technicianId(technicianId)
                .comment("Updated comment")
                .rating(3)
                .build();

        Review result = reviewRepository.save(updatedReview);

        assertEquals(updatedReview.getId(), result.getId());
        assertEquals(updatedReview.getUserId(), result.getUserId());
        assertEquals(updatedReview.getTechnicianId(), result.getTechnicianId());
        assertEquals("Updated comment", result.getComment());
        assertEquals(3, result.getRating());
    }

    @Test
    void testFindById() {
        reviewRepository.save(review1);
        reviewRepository.save(review2);

        Review foundReview = reviewRepository.findById(reviewId1);

        assertNotNull(foundReview);
        assertEquals(review1.getId(), foundReview.getId());
        assertEquals(review1.getComment(), foundReview.getComment());
    }

    @Test
    void testFindByIdNotFound() {
        reviewRepository.save(review1);

        Review foundReview = reviewRepository.findById(UUID.randomUUID());

        assertNull(foundReview);
    }

    @Test
    void testFindAll() {
        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> allReviews = reviewRepository.findAll();

        assertEquals(2, allReviews.size());
        assertTrue(allReviews.contains(review1));
        assertTrue(allReviews.contains(review2));
    }

    @Test
    void testFindByTechnicianId() {
        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> technicianReviews = reviewRepository.findByTechnicianId(technicianId);

        assertEquals(2, technicianReviews.size());
        assertTrue(technicianReviews.contains(review1));
        assertTrue(technicianReviews.contains(review2));
    }

    @Test
    void testFindByUserId() {
        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> userReviews = reviewRepository.findByUserId(userId1);

        assertEquals(1, userReviews.size());
        assertTrue(userReviews.contains(review1));
    }

    @Test
    void testDelete() {
        reviewRepository.save(review1);
        reviewRepository.save(review2);

        reviewRepository.delete(reviewId1);

        assertNull(reviewRepository.findById(reviewId1));
        assertNotNull(reviewRepository.findById(reviewId2));
    }

    @Test
    void testDeleteNotFound() {
        reviewRepository.save(review1);

        UUID randomId = UUID.randomUUID();
        reviewRepository.delete(randomId);

        assertNotNull(reviewRepository.findById(review1.getId()));
        assertEquals(1, reviewRepository.findAll().size());
    }
}