package id.ac.ui.cs.advprog.review.repository;

import id.ac.ui.cs.advprog.review.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, UUID> {
}