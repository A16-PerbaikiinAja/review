package id.ac.ui.cs.advprog.review.service;

import id.ac.ui.cs.advprog.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.review.dto.ReviewResponseDTO;
import id.ac.ui.cs.advprog.review.dto.TechnicianRatingDTO;
import id.ac.ui.cs.advprog.review.model.Review;
import id.ac.ui.cs.advprog.review.model.Technician;
import id.ac.ui.cs.advprog.review.repository.ReviewRepository;
import id.ac.ui.cs.advprog.review.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final TechnicianRepository technicianRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             TechnicianRepository technicianRepository) {
        this.reviewRepository = reviewRepository;
        this.technicianRepository = technicianRepository;
    }

    @Override
    public Review createReview(ReviewDTO reviewDTO) {
        UUID reviewId = reviewDTO.getId() != null ? reviewDTO.getId() : UUID.randomUUID();

        Review newReview = Review.builder()
                .id(reviewId)
                .userId(reviewDTO.getUserId())
                .technicianId(reviewDTO.getTechnicianId())
                .comment(reviewDTO.getComment())
                .rating(reviewDTO.getRating())
                .build();

        return reviewRepository.save(newReview);
    }

    @Override
    public Review getReviewById(UUID id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByTechnicianId(UUID technicianId) {
        return reviewRepository.findByTechnicianId(technicianId);
    }

    @Override
    public List<Review> getReviewsByUserId(UUID userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public Review updateReview(UUID id, ReviewDTO reviewDTO) {
        Optional<Review> existingReviewOpt = reviewRepository.findById(id);

        if (existingReviewOpt.isEmpty()) {
            return null;
        }

        Review existingReview = existingReviewOpt.get();

        Review updatedReview = Review.builderFromExisting(existingReview)
                .comment(reviewDTO.getComment())
                .rating(reviewDTO.getRating())
                .build();

        return reviewRepository.save(updatedReview);
    }

    @Override
    public boolean deleteReview(UUID id, UUID userId) {
        Optional<Review> existingReviewOpt = reviewRepository.findById(id);

        if (existingReviewOpt.isEmpty()) {
            return false;
        }

        Review existingReview = existingReviewOpt.get();

        // Check if the user is the owner of the review
        if (!existingReview.getUserId().equals(userId)) {
            return false;
        }

        reviewRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteReviewByAdmin(UUID id) {
        if (!reviewRepository.existsById(id)) {
            return false;
        }

        reviewRepository.deleteById(id);
        return true;
    }

    @Override
    public ReviewResponseDTO getReviewResponseById(UUID id) {
        Review review = getReviewById(id);
        if (review == null) {
            return null;
        }

        return createReviewResponseDTO(review);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviewResponses() {
        List<Review> reviews = getAllReviews();
        return reviews.stream().map(this::createReviewResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewResponsesByTechnicianId(UUID technicianId) {
        List<Review> reviews = getReviewsByTechnicianId(technicianId);
        return reviews.stream().map(this::createReviewResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewResponsesByUserId(UUID userId) {
        List<Review> reviews = getReviewsByUserId(userId);
        return reviews.stream().map(this::createReviewResponseDTO).collect(Collectors.toList());
    }

    private ReviewResponseDTO createReviewResponseDTO(Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setUserId(review.getUserId());
        dto.setTechnicianId(review.getTechnicianId());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setCreatedAt(review.getCreatedAt());

        technicianRepository.findById(review.getTechnicianId()).ifPresent(technician ->
                dto.setTechnicianFullName(technician.getFullName())
        );

        return dto;
    }

    @Override
    public Double getAverageRatingByTechnicianId(UUID technicianId) {
        // Gunakan query method yang efisien
        Double averageRating = reviewRepository.findAverageRatingByTechnicianId(technicianId);
        return averageRating != null ? averageRating : 0.0;
    }

    @Override
    public TechnicianRatingDTO getTechnicianWithRating(UUID technicianId) {
        Optional<Technician> technicianOpt = technicianRepository.findById(technicianId);
        if (technicianOpt.isEmpty()) {
            return null;
        }

        Technician technician = technicianOpt.get();

        Double averageRating = reviewRepository.findAverageRatingByTechnicianId(technicianId);
        Integer totalReviews = reviewRepository.countByTechnicianId(technicianId);

        TechnicianRatingDTO dto = new TechnicianRatingDTO();
        dto.setTechnicianId(technician.getId());
        dto.setFullName(technician.getFullName());
        dto.setProfilePhoto(technician.getProfilePhoto());
        dto.setExperience(technician.getExperience());
        dto.setSpecialization(technician.getExperience() != null ?
                "Experience: " + technician.getExperience() + " years" : null);
        dto.setTotalReviews(totalReviews != null ? totalReviews : 0);
        dto.setAverageRating(averageRating != null ? averageRating : 0.0);

        return dto;
    }

    @Override
    public List<TechnicianRatingDTO> getAllTechniciansWithRating() {
        List<Technician> technicians = technicianRepository.findAll();

        return technicians.stream().map(technician -> {
            UUID technicianId = technician.getId();

            Double averageRating = reviewRepository.findAverageRatingByTechnicianId(technicianId);
            Integer totalReviews = reviewRepository.countByTechnicianId(technicianId);

            TechnicianRatingDTO dto = new TechnicianRatingDTO();
            dto.setTechnicianId(technician.getId());
            dto.setFullName(technician.getFullName());
            dto.setProfilePhoto(technician.getProfilePhoto());
            dto.setExperience(technician.getExperience());
            dto.setSpecialization(technician.getExperience() != null ?
                    "Experience: " + technician.getExperience() + " years" : null);
            dto.setTotalReviews(totalReviews != null ? totalReviews : 0);
            dto.setAverageRating(averageRating != null ? averageRating : 0.0);

            return dto;
        }).collect(Collectors.toList());
    }
}