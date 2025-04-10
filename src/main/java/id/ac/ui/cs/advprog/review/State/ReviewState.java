package id.ac.ui.cs.advprog.review.State;

import id.ac.ui.cs.advprog.review.model.Review;

public interface ReviewState {
    void approve(Review review);
    void reject(Review review);
    String getState();
}
