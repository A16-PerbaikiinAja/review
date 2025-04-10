package id.ac.ui.cs.advprog.review.state;

import id.ac.ui.cs.advprog.review.model.Review;

public class RejectedState implements ReviewState {
    @Override
    public void approve(Review review) {
        // Cannot approve after rejected
    }

    @Override
    public void reject(Review review) {
        // Already rejected, do nothing
    }

    @Override
    public String getState() {
        return "REJECTED";
    }
}
