package id.ac.ui.cs.advprog.review.state;

import enums.Status;
import id.ac.ui.cs.advprog.review.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewStateTest {

    private Review review;

    @BeforeEach
    public void setUp() {
        review = new Review("1", 5, "Pengerjaannya oke nih!", "4321", "box127");
    }

    @Test
    public void testApproveFromPending() {
        review.approve();
        assertEquals(Status.APPROVED, review.getReviewStatus());
        assertEquals("APPROVED", review.getStateString());
    }

    @Test
    public void testRejectFromPending() {
        review.reject();
        assertEquals(Status.REJECTED, review.getReviewStatus());
        assertEquals("REJECTED", review.getStateString());
    }

    @Test
    public void testApproveFromApprovedStateDoesNothing() {
        review.approve(); // First approve
        review.approve(); // Try approving again

        assertEquals(Status.APPROVED, review.getReviewStatus());
        assertEquals("APPROVED", review.getStateString());
    }

    @Test
    public void testRejectFromRejectedStateDoesNothing() {
        review.reject(); // First reject
        review.reject(); // Try rejecting again

        assertEquals(Status.REJECTED, review.getReviewStatus());
        assertEquals("REJECTED", review.getStateString());
    }
}
