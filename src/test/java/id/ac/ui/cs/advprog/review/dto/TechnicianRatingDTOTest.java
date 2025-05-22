package id.ac.ui.cs.advprog.review.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TechnicianRatingDTOTest {
    private TechnicianRatingDTO technicianRatingDTO;
    private final UUID testTechnicianId = UUID.randomUUID();
    private final String testFullName = "Jane Smith";
    private final String testProfilePhoto = "profile.jpg";
    private final String testSpecialization = "Electronics";
    private final Integer testExperience = 5;
    private final Double testAverageRating = 4.5;
    private final Integer testTotalReviews = 10;

    @BeforeEach
    void setUp() {
        technicianRatingDTO = new TechnicianRatingDTO();
    }

    @Test
    void testGetAndSetTechnicianId() {
        technicianRatingDTO.setTechnicianId(testTechnicianId);
        assertEquals(testTechnicianId, technicianRatingDTO.getTechnicianId());
    }

    @Test
    void testGetAndSetFullName() {
        technicianRatingDTO.setFullName(testFullName);
        assertEquals(testFullName, technicianRatingDTO.getFullName());
    }

    @Test
    void testGetAndSetProfilePhoto() {
        technicianRatingDTO.setProfilePhoto(testProfilePhoto);
        assertEquals(testProfilePhoto, technicianRatingDTO.getProfilePhoto());
    }

    @Test
    void testGetAndSetSpecialization() {
        technicianRatingDTO.setSpecialization(testSpecialization);
        assertEquals(testSpecialization, technicianRatingDTO.getSpecialization());
    }

    @Test
    void testGetAndSetExperience() {
        technicianRatingDTO.setExperience(testExperience);
        assertEquals(testExperience, technicianRatingDTO.getExperience());
    }

    @Test
    void testGetAndSetAverageRating() {
        technicianRatingDTO.setAverageRating(testAverageRating);
        assertEquals(testAverageRating, technicianRatingDTO.getAverageRating());
    }

    @Test
    void testGetAndSetTotalReviews() {
        technicianRatingDTO.setTotalReviews(testTotalReviews);
        assertEquals(testTotalReviews, technicianRatingDTO.getTotalReviews());
    }

    @Test
    void testDefaultValues() {
        assertNull(technicianRatingDTO.getTechnicianId());
        assertNull(technicianRatingDTO.getFullName());
        assertNull(technicianRatingDTO.getProfilePhoto());
        assertNull(technicianRatingDTO.getSpecialization());
        assertNull(technicianRatingDTO.getExperience());
        assertNull(technicianRatingDTO.getAverageRating());
        assertNull(technicianRatingDTO.getTotalReviews());
    }
}