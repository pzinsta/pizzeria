package pzinsta.exception;

public class PizzaSizeNotFoundException extends RuntimeException {
    public PizzaSizeNotFoundException(Long id) {
        this(String.format("Pizza size with ID %s not found.", id));
    }

    public PizzaSizeNotFoundException(String message) {
        super(message);
    }
}
