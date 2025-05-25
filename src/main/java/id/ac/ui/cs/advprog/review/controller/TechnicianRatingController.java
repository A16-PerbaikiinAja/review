package id.ac.ui.cs.advprog.review.controller;

import id.ac.ui.cs.advprog.review.dto.TechnicianRatingDTO;
import id.ac.ui.cs.advprog.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/technician-ratings")
public class TechnicianRatingController {

    private final ReviewService reviewService;

    @Autowired
    public TechnicianRatingController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<TechnicianRatingDTO>> getAllTechniciansWithRating() {
        List<TechnicianRatingDTO> technicians = reviewService.getAllTechniciansWithRating();
        return ResponseEntity.ok(technicians);
    }

    @GetMapping("/{technicianId}")
    public ResponseEntity<TechnicianRatingDTO> getTechnicianWithRating(@PathVariable UUID technicianId) {
        TechnicianRatingDTO technician = reviewService.getTechnicianWithRating(technicianId);
        if (technician == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(technician);
    }

    @GetMapping("/average/{technicianId}")
    public ResponseEntity<Double> getAverageRatingByTechnicianId(@PathVariable UUID technicianId) {
        Double averageRating = reviewService.getAverageRatingByTechnicianId(technicianId);
        return ResponseEntity.ok(averageRating);
    }
}