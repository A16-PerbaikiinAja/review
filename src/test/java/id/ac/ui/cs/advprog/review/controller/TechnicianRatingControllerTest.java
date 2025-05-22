package id.ac.ui.cs.advprog.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import id.ac.ui.cs.advprog.review.dto.TechnicianRatingDTO;
import id.ac.ui.cs.advprog.review.security.JwtTokenProvider;
import id.ac.ui.cs.advprog.review.service.ReviewService;
import id.ac.ui.cs.advprog.review.config.SecurityConfig;

@WebMvcTest(TechnicianRatingController.class)
@Import(SecurityConfig.class)
public class TechnicianRatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private UUID technicianId;
    private TechnicianRatingDTO testTechnicianRatingDTO;

    @BeforeEach
    void setUp() {
        technicianId = UUID.randomUUID();

        testTechnicianRatingDTO = new TechnicianRatingDTO();
        testTechnicianRatingDTO.setTechnicianId(technicianId);
        testTechnicianRatingDTO.setFullName("Jane Smith");
        testTechnicianRatingDTO.setProfilePhoto("profile.jpg");
        testTechnicianRatingDTO.setSpecialization("Electronics");
        testTechnicianRatingDTO.setExperience(5);
        testTechnicianRatingDTO.setAverageRating(4.5);
        testTechnicianRatingDTO.setTotalReviews(10);
    }

    @Test
    @WithMockUser
    void testGetAllTechniciansWithRating() throws Exception {
        List<TechnicianRatingDTO> technicians = Arrays.asList(testTechnicianRatingDTO);
        Mockito.when(reviewService.getAllTechniciansWithRating()).thenReturn(technicians);

        mockMvc.perform(get("/technician-ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].technicianId").value(technicianId.toString()))
                .andExpect(jsonPath("$[0].fullName").value("Jane Smith"))
                .andExpect(jsonPath("$[0].averageRating").value(4.5))
                .andExpect(jsonPath("$[0].totalReviews").value(10));
    }

    @Test
    @WithMockUser
    void testGetTechnicianWithRating() throws Exception {
        Mockito.when(reviewService.getTechnicianWithRating(technicianId)).thenReturn(testTechnicianRatingDTO);

        mockMvc.perform(get("/technician-ratings/" + technicianId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.technicianId").value(technicianId.toString()))
                .andExpect(jsonPath("$.fullName").value("Jane Smith"))
                .andExpect(jsonPath("$.averageRating").value(4.5))
                .andExpect(jsonPath("$.totalReviews").value(10));
    }

    @Test
    @WithMockUser
    void testGetTechnicianWithRatingNotFound() throws Exception {
        Mockito.when(reviewService.getTechnicianWithRating(technicianId)).thenReturn(null);

        mockMvc.perform(get("/technician-ratings/" + technicianId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testGetAverageRatingByTechnicianId() throws Exception {
        Mockito.when(reviewService.getAverageRatingByTechnicianId(technicianId)).thenReturn(4.5);

        mockMvc.perform(get("/technician-ratings/average/" + technicianId))
                .andExpect(status().isOk())
                .andExpect(content().string("4.5"));
    }

    @Test
    @WithMockUser
    void testGetAverageRatingByTechnicianIdNoRating() throws Exception {
        Mockito.when(reviewService.getAverageRatingByTechnicianId(technicianId)).thenReturn(0.0);

        mockMvc.perform(get("/technician-ratings/average/" + technicianId))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));
    }
}