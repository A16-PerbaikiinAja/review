package id.ac.ui.cs.advprog.review.State;

import id.ac.ui.cs.advprog.review.model.Review;

public class PendingState implements ReviewState {
    @Override
    public void approve(Review review) {
        review.setState(new ApprovedState());
        review.setReviewStatus(enums.Status.APPROVED);
    }

    @Override
    public void reject(Review review) {
        review.setState(new RejectedState());
        review.setReviewStatus(enums.Status.REJECTED);
    }

    @Override
    public String getState() {
        return "PENDING";
    }
}
