package pzinsta.pizzeria.service.dto;

import java.util.Collection;

public class ReviewDTO {
    private String message;
    private int rating;
    private Collection<Long> fileIds;

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

    public Collection<Long> getFiles() {
        return fileIds;
    }

    public void setFiles(Collection<Long> fileIds) {
        this.fileIds = fileIds;
    }
}
