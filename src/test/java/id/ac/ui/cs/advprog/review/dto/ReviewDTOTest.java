package id.ac.ui.cs.advprog.review.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewDTOTest {
    private ReviewDTO reviewDTO;
    private final UUID testId = UUID.randomUUID();
    private final UUID testUserId = UUID.randomUUID();
    private final UUID testTechnicianId = UUID.randomUUID();
    private final String testComment = "Pengerjaannya sangat oke!";
    private final int testRating = 5;

    @BeforeEach
    void setUp() {
        reviewDTO = new ReviewDTO();
    }

    @Test
    void testGetAndSetId() {
        reviewDTO.setId(testId);
        assertEquals(testId, reviewDTO.getId());
    }

    @Test
    void testGetAndSetUserId() {
        reviewDTO.setUserId(testUserId);
        assertEquals(testUserId, reviewDTO.getUserId());
    }

    @Test
    void testGetAndSetTechnicianId() {
        reviewDTO.setTechnicianId(testTechnicianId);
        assertEquals(testTechnicianId, reviewDTO.getTechnicianId());
    }

    @Test
    void testGetAndSetComment() {
        reviewDTO.setComment(testComment);
        assertEquals(testComment, reviewDTO.getComment());
    }

    @Test
    void testGetAndSetRating() {
        reviewDTO.setRating(testRating);
        assertEquals(testRating, reviewDTO.getRating());
    }

    @Test
    void testDefaultValues() {
        assertNull(reviewDTO.getId());
        assertNull(reviewDTO.getUserId());
        assertNull(reviewDTO.getTechnicianId());
        assertNull(reviewDTO.getComment());
        assertEquals(0, reviewDTO.getRating());
    }
}