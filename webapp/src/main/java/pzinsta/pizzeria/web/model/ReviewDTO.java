package pzinsta.pizzeria.web.model;

import pzinsta.pizzeria.web.client.dto.File;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public class ReviewDTO {
    private Instant createdOn;
    private Instant lastUpdatedOn;
    private String message;
    private int rating;
    private Collection<File> images = new ArrayList<>();

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Instant lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
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

    public Collection<File> getImages() {
        return images;
    }

    public void setImages(Collection<File> images) {
        this.images = images;
    }
}
