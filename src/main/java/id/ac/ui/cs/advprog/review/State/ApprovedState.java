package id.ac.ui.cs.advprog.review.State;

import id.ac.ui.cs.advprog.review.model.Review;

public class ApprovedState implements ReviewState {
    @Override
    public void approve(Review review) {
        // Already approved
    }

    @Override
    public void reject(Review review) {
        // Cannot reject after approved
    }

    @Override
    public String getState() {
        return "APPROVED";
    }
}
