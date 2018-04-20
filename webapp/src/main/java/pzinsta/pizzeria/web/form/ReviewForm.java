package pzinsta.pizzeria.web.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ReviewForm {
    @Length(max = 1000)
    private String message;

    @Min(1)
    @Max(10)
    private int rating;

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
}
