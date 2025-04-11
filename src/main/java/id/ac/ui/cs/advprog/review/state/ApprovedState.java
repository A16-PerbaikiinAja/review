package id.ac.ui.cs.advprog.review.state;

import id.ac.ui.cs.advprog.review.model.Review;

public class ApprovedState implements ReviewState {
    @Override
    public void approve(Review review) {
        // Already approved, do nothing
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
