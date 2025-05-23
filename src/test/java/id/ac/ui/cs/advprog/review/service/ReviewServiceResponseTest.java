package id.ac.ui.cs.advprog.review.service;

import id.ac.ui.cs.advprog.review.dto.ReviewResponseDTO;
import id.ac.ui.cs.advprog.review.dto.TechnicianRatingDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.model.Technician;
import id.ac.ui.cs.advprog.review.repository.ReviewRepository;
import id.ac.ui.cs.advprog.review.repository.TechnicianRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceResponseTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    private ReviewService reviewService;

    private UUID reviewId;
    private UUID userId;
    private UUID technicianId;
    private Review testReview;
    private Technician testTechnician;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewServiceImpl(reviewRepository, technicianRepository);

        reviewId = UUID.randomUUID();
        userId = UUID.randomUUID();
        technicianId = UUID.randomUUID();

        testReview = Review.builder()
                .id(reviewId)
                .userId(userId)
                .technicianId(technicianId)
                .comment("Pengerjaannya sangat oke!")
                .rating(5)
                .build();

        testTechnician = mock(Technician.class);
    }

    @Test
    void testGetReviewResponseById() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(testReview));
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(testTechnician));
        when(testTechnician.getFullName()).thenReturn("Jane Smith");

        ReviewResponseDTO result = reviewService.getReviewResponseById(reviewId);

        assertNotNull(result);
        assertEquals(reviewId, result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(technicianId, result.getTechnicianId());
        assertEquals("Jane Smith", result.getTechnicianFullName());
        assertEquals("Pengerjaannya sangat oke!", result.getComment());
        assertEquals(5, result.getRating());
    }

    @Test
    void testGetReviewResponseByIdNotFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        ReviewResponseDTO result = reviewService.getReviewResponseById(reviewId);

        assertNull(result);
    }

    @Test
    void testGetAllReviewResponses() {
        when(reviewRepository.findAll()).thenReturn(List.of(testReview));
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(testTechnician));
        when(testTechnician.getFullName()).thenReturn("Jane Smith");

        List<ReviewResponseDTO> results = reviewService.getAllReviewResponses();

        assertEquals(1, results.size());
        assertEquals(reviewId, results.get(0).getId());
        assertEquals("Jane Smith", results.get(0).getTechnicianFullName());
    }

    @Test
    void testGetReviewResponsesByTechnicianId() {
        when(reviewRepository.findByTechnicianId(technicianId)).thenReturn(List.of(testReview));
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(testTechnician));
        when(testTechnician.getFullName()).thenReturn("Jane Smith");

        List<ReviewResponseDTO> results = reviewService.getReviewResponsesByTechnicianId(technicianId);

        assertEquals(1, results.size());
        assertEquals(reviewId, results.get(0).getId());
        assertEquals("Jane Smith", results.get(0).getTechnicianFullName());
    }

    @Test
    void testGetReviewResponsesByUserId() {
        when(reviewRepository.findByUserId(userId)).thenReturn(List.of(testReview));
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(testTechnician));
        when(testTechnician.getFullName()).thenReturn("Jane Smith");

        List<ReviewResponseDTO> results = reviewService.getReviewResponsesByUserId(userId);

        assertEquals(1, results.size());
        assertEquals(reviewId, results.get(0).getId());
        assertEquals("Jane Smith", results.get(0).getTechnicianFullName());
    }

    @Test
    void testGetAverageRatingByTechnicianId() {
        when(reviewRepository.findAverageRatingByTechnicianId(technicianId)).thenReturn(4.5);

        Double result = reviewService.getAverageRatingByTechnicianId(technicianId);

        assertEquals(4.5, result);
    }

    @Test
    void testGetAverageRatingByTechnicianIdNoReviews() {
        when(reviewRepository.findAverageRatingByTechnicianId(technicianId)).thenReturn(null);

        Double result = reviewService.getAverageRatingByTechnicianId(technicianId);

        assertEquals(0.0, result);
    }

    @Test
    void testGetTechnicianWithRating() {
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(testTechnician));
        when(reviewRepository.findAverageRatingByTechnicianId(technicianId)).thenReturn(4.5);
        when(reviewRepository.countByTechnicianId(technicianId)).thenReturn(10);

        when(testTechnician.getId()).thenReturn(technicianId);
        when(testTechnician.getFullName()).thenReturn("Jane Smith");
        when(testTechnician.getProfilePhoto()).thenReturn("profile.jpg");
        when(testTechnician.getExperience()).thenReturn(5);

        TechnicianRatingDTO result = reviewService.getTechnicianWithRating(technicianId);

        assertNotNull(result);
        assertEquals(technicianId, result.getTechnicianId());
        assertEquals("Jane Smith", result.getFullName());
        assertEquals("profile.jpg", result.getProfilePhoto());
        assertEquals(5, result.getExperience());
        assertEquals(4.5, result.getAverageRating());
        assertEquals(10, result.getTotalReviews());
    }

    @Test
    void testGetTechnicianWithRatingNotFound() {
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.empty());

        TechnicianRatingDTO result = reviewService.getTechnicianWithRating(technicianId);

        assertNull(result);
    }

    @Test
    void testGetAllTechniciansWithRating() {
        when(technicianRepository.findAll()).thenReturn(List.of(testTechnician));
        when(reviewRepository.findAverageRatingByTechnicianId(technicianId)).thenReturn(4.5);
        when(reviewRepository.countByTechnicianId(technicianId)).thenReturn(10);

        when(testTechnician.getId()).thenReturn(technicianId);
        when(testTechnician.getFullName()).thenReturn("Jane Smith");
        when(testTechnician.getProfilePhoto()).thenReturn("profile.jpg");
        when(testTechnician.getExperience()).thenReturn(5);

        List<TechnicianRatingDTO> results = reviewService.getAllTechniciansWithRating();

        assertEquals(1, results.size());
        assertEquals(technicianId, results.get(0).getTechnicianId());
        assertEquals("Jane Smith", results.get(0).getFullName());
        assertEquals(4.5, results.get(0).getAverageRating());
        assertEquals(10, results.get(0).getTotalReviews());
    }
}