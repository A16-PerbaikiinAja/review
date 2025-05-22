package id.ac.ui.cs.advprog.review.repository;

import id.ac.ui.cs.advprog.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByTechnicianId(UUID technicianId);
    List<Review> findByUserId(UUID userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.technicianId = :technicianId")
    Double findAverageRatingByTechnicianId(@Param("technicianId") UUID technicianId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.technicianId = :technicianId")
    Integer countByTechnicianId(@Param("technicianId") UUID technicianId);
}