package id.ac.ui.cs.advprog.review.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "technicians")
public class Technician {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "total_earnings", nullable = false)
    private Double totalEarnings;

    @Column(name = "total_jobs_completed", nullable = false)
    private Integer totalJobsCompleted;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Technician() {
        // Default constructor for JPA
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public Integer getExperience() {
        return experience;
    }

    public Double getTotalEarnings() {
        return totalEarnings;
    }

    public Integer getTotalJobsCompleted() {
        return totalJobsCompleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}