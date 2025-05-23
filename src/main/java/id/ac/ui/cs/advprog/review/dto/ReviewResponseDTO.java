package id.ac.ui.cs.advprog.review.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReviewResponseDTO {
    private UUID id;
    private UUID userId;
    private UUID technicianId;
    private String technicianFullName;
    private String comment;
    private int rating;
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(UUID technicianId) {
        this.technicianId = technicianId;
    }

    public String getTechnicianFullName() {
        return technicianFullName;
    }

    public void setTechnicianFullName(String technicianFullName) {
        this.technicianFullName = technicianFullName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}