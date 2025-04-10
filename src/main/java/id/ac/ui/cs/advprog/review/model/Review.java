package id.ac.ui.cs.advprog.review.model;

import enums.Status;
import id.ac.ui.cs.advprog.review.state.*;

public class Review {

    private String reviewId;
    private int ratingScore;
    private String review;
    private String userId;
    private String subscriptionBoxId;

    private Status reviewStatus;
    private ReviewState state;

    public Review(String reviewId, int ratingScore, String review, String userId, String subscriptionBoxId) {
        this.reviewId = reviewId;
        this.ratingScore = ratingScore;
        this.review = review;
        this.userId = userId;
        this.subscriptionBoxId = subscriptionBoxId;

        // Default initial state is Pending
        this.state = new PendingState();
        this.reviewStatus = Status.PENDING;
    }

    // State management
    public void approve() {
        state.approve(this);
    }

    public void reject() {
        state.reject(this);
    }

    public String getStateString() {
        return state.getState();
    }

    // Setters for state and status
    public void setState(ReviewState state) {
        this.state = state;
    }

    public void setReviewStatus(Status reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    // Getters
    public String getReviewId() {
        return reviewId;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public String getReview() {
        return review;
    }

    public String getUserId() {
        return userId;
    }

    public String getSubscriptionBoxId() {
        return subscriptionBoxId;
    }

    public Status getReviewStatus() {
        return reviewStatus;
    }

    public ReviewState getState() {
        return state;
    }

    // Setters
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSubscriptionBoxId(String subscriptionBoxId) {
        this.subscriptionBoxId = subscriptionBoxId;
    }
}
