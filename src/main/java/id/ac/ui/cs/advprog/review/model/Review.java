package id.ac.ui.cs.advprog.review.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {

    private final UUID id;
    private final UUID userId;
    private final UUID technicianId;
    private final String comment;
    private final int rating;
    private final LocalDateTime createdAt;

    private Review(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.technicianId = builder.technicianId;
        this.comment = builder.comment;
        this.rating = builder.rating;
        this.createdAt = builder.createdAt != null ? builder.createdAt : LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTechnicianId() {
        return technicianId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private UUID userId;
        private UUID technicianId;
        private String comment;
        private int rating;
        private LocalDateTime createdAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder technicianId(UUID technicianId) {
            this.technicianId = technicianId;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Review build() {
            if (id == null) {
                throw new IllegalArgumentException("Review id is required");
            }
            if (userId == null) {
                throw new IllegalArgumentException("User id is required");
            }
            if (technicianId == null) {
                throw new IllegalArgumentException("Technician id is required");
            }
            if (comment == null || comment.trim().isEmpty()) {
                throw new IllegalArgumentException("Comment is required");
            }
            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException("Rating must be between 1 and 5");
            }
            return new Review(this);
        }
    }
}

