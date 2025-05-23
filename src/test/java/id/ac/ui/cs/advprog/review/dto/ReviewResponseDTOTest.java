package id.ac.ui.cs.advprog.review.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewResponseDTOTest {
    private ReviewResponseDTO reviewResponseDTO;
    private final UUID testId = UUID.randomUUID();
    private final UUID testUserId = UUID.randomUUID();
    private final UUID testTechnicianId = UUID.randomUUID();
    private final String testTechnicianFullName = "Jane Smith";
    private final String testComment = "Pengerjaannya sangat oke!";
    private final int testRating = 5;
    private final LocalDateTime testCreatedAt = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        reviewResponseDTO = new ReviewResponseDTO();
    }

    @Test
    void testGetAndSetId() {
        reviewResponseDTO.setId(testId);
        assertEquals(testId, reviewResponseDTO.getId());
    }

    @Test
    void testGetAndSetUserId() {
        reviewResponseDTO.setUserId(testUserId);
        assertEquals(testUserId, reviewResponseDTO.getUserId());
    }

    @Test
    void testGetAndSetTechnicianId() {
        reviewResponseDTO.setTechnicianId(testTechnicianId);
        assertEquals(testTechnicianId, reviewResponseDTO.getTechnicianId());
    }

    @Test
    void testGetAndSetTechnicianFullName() {
        reviewResponseDTO.setTechnicianFullName(testTechnicianFullName);
        assertEquals(testTechnicianFullName, reviewResponseDTO.getTechnicianFullName());
    }

    @Test
    void testGetAndSetComment() {
        reviewResponseDTO.setComment(testComment);
        assertEquals(testComment, reviewResponseDTO.getComment());
    }

    @Test
    void testGetAndSetRating() {
        reviewResponseDTO.setRating(testRating);
        assertEquals(testRating, reviewResponseDTO.getRating());
    }

    @Test
    void testGetAndSetCreatedAt() {
        reviewResponseDTO.setCreatedAt(testCreatedAt);
        assertEquals(testCreatedAt, reviewResponseDTO.getCreatedAt());
    }

    @Test
    void testDefaultValues() {
        assertNull(reviewResponseDTO.getId());
        assertNull(reviewResponseDTO.getUserId());
        assertNull(reviewResponseDTO.getTechnicianId());
        assertNull(reviewResponseDTO.getTechnicianFullName());
        assertNull(reviewResponseDTO.getComment());
        assertEquals(0, reviewResponseDTO.getRating());
        assertNull(reviewResponseDTO.getCreatedAt());
    }
}