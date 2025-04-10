package id.ac.ui.cs.advprog.review.State;

import id.ac.ui.cs.advprog.review.model.Review;

public class RejectedState implements ReviewState {
    @Override
    public void approve(Review review) {
        // Cannot approve after rejected
    }

    @Override
    public void reject(Review review) {
        // Already rejected
    }

    @Override
    public String getState() {
        return "REJECTED";
    }
}
