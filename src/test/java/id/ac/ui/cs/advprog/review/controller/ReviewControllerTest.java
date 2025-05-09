package id.ac.ui.cs.advprog.review.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.security.JwtTokenProvider;
import id.ac.ui.cs.advprog.review.service.ReviewService;
import id.ac.ui.cs.advprog.review.config.SecurityConfig;

@WebMvcTest(ReviewController.class)
@Import(SecurityConfig.class)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID fixedUserId;
    private UUID reviewId;
    private UUID technicianId;
    private Review testReview;
    private ReviewDTO testReviewDTO;

    @BeforeEach
    void setUp() {
        fixedUserId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        reviewId = UUID.randomUUID();
        technicianId = UUID.randomUUID();

        testReview = Review.builder()
                .id(reviewId)
                .userId(fixedUserId)
                .technicianId(technicianId)
                .comment("Great service!")
                .rating(5)
                .build();

        testReviewDTO = new ReviewDTO();
        testReviewDTO.setId(reviewId);
        testReviewDTO.setUserId(fixedUserId);
        testReviewDTO.setTechnicianId(technicianId);
        testReviewDTO.setComment("Great service!");
        testReviewDTO.setRating(5);
    }

    @Test
    void testGetAllReviews() throws Exception {
        List<Review> reviews = Arrays.asList(testReview);
        Mockito.when(reviewService.getAllReviews()).thenReturn(reviews);

        mockMvc.perform(get("/review"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(reviewId.toString()))
                .andExpect(jsonPath("$[0].comment").value("Great service!"));
    }

    @Test
    void testGetReviewsByTechnicianId() throws Exception {
        List<Review> reviews = Arrays.asList(testReview);
        Mockito.when(reviewService.getReviewsByTechnicianId(technicianId)).thenReturn(reviews);

        mockMvc.perform(get("/review/technician/" + technicianId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(reviewId.toString()));
    }

    @Test
    @WithMockUser(username = "00000000-0000-0000-0000-000000000001", roles = {"USER"})
    void testGetUserReviews() throws Exception {
        List<Review> reviews = Arrays.asList(testReview);
        Mockito.when(reviewService.getReviewsByUserId(fixedUserId)).thenReturn(reviews);

        mockMvc.perform(get("/review/user").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(reviewId.toString()));
    }

    @Test
    void testGetReviewById() throws Exception {
        Mockito.when(reviewService.getReviewById(reviewId)).thenReturn(testReview);

        mockMvc.perform(get("/review/" + reviewId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reviewId.toString()));
    }

    @Test
    void testGetReviewByIdNotFound() throws Exception {
        Mockito.when(reviewService.getReviewById(reviewId)).thenReturn(null);

        mockMvc.perform(get("/review/" + reviewId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "00000000-0000-0000-0000-000000000001", roles = {"USER"})
    void testCreateReview() throws Exception {
        Mockito.when(reviewService.createReview(Mockito.any(ReviewDTO.class))).thenReturn(testReview);

        mockMvc.perform(post("/review")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testReviewDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reviewId.toString()));
    }

    @Test
    @WithMockUser(username = "00000000-0000-0000-0000-000000000001", roles = {"USER"})
    void testUpdateReview() throws Exception {
        Mockito.when(reviewService.getReviewById(reviewId)).thenReturn(testReview);
        Mockito.when(reviewService.updateReview(Mockito.eq(reviewId), Mockito.any(ReviewDTO.class)))
                .thenReturn(testReview);

        mockMvc.perform(put("/review/" + reviewId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testReviewDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "00000000-0000-0000-0000-000000000002", roles = {"USER"})
    void testUpdateReviewForbidden() throws Exception {
        // Different user trying to update
        Mockito.when(reviewService.getReviewById(reviewId)).thenReturn(testReview);

        mockMvc.perform(put("/review/" + reviewId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testReviewDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Hanya bisa merubah review yang Anda buat sendiri!"));
    }

    @Test
    @WithMockUser(username = "00000000-0000-0000-0000-000000000001", roles = {"USER"})
    void testDeleteReviewAsUser() throws Exception {
        Mockito.when(reviewService.deleteReview(reviewId, fixedUserId)).thenReturn(true);

        mockMvc.perform(delete("/review/" + reviewId).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "00000000-0000-0000-0000-000000000001", roles = {"ADMIN"})
    void testDeleteReviewAsAdmin() throws Exception {
        Mockito.when(reviewService.deleteReviewByAdmin(reviewId)).thenReturn(true);

        mockMvc.perform(delete("/review/" + reviewId).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "00000000-0000-0000-0000-000000000001", roles = {"USER"})
    void testDeleteReviewNotFound() throws Exception {
        Mockito.when(reviewService.deleteReview(reviewId, fixedUserId)).thenReturn(false);

        mockMvc.perform(delete("/review/" + reviewId).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Review tidak ditemukan atau Anda tidak punya permission."));
    }
}