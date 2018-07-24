package pzinsta.pizzeria.web.client.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public class Review {
    private Long reviewId;
    private Long orderId;
    private Instant createdOn;
    private Instant lastUpdatedOn;
    private String message;
    private int rating;
    private Collection<Long> images = new ArrayList<>();

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Instant lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Collection<Long> getImages() {
        return images;
    }

    public void setImages(Collection<Long> images) {
        this.images = images;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
