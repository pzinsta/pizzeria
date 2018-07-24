package pzinsta.pizzeria.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(Long id) {
        this(String.format("Review with ID %s not found.", id));
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
