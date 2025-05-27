package id.ac.ui.cs.advprog.review.controller;

import id.ac.ui.cs.advprog.review.dto.TechnicianRatingDTO;
import id.ac.ui.cs.advprog.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<TechnicianRatingDTO>> getAllTechniciansWithRating() {
        List<TechnicianRatingDTO> technicians = reviewService.getAllTechniciansWithRating();
        return ResponseEntity.ok(technicians);
    }

    @GetMapping("/technician")
    @PreAuthorize("hasRole('TECHNICIAN')")
    public ResponseEntity<TechnicianRatingDTO> getThisTechnicianWithRating(Authentication auth) {
        UUID technicianId = null;
        if (auth != null) {
            try {
                technicianId = extractUserId(auth);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to extract technician ID", e);
            }
        }

        TechnicianRatingDTO result = reviewService.getTechnicianWithRating(technicianId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}