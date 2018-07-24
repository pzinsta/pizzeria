package pzinsta.pizzeria.web.exception;

public class CutStyleNotFoundException extends RuntimeException {
    public CutStyleNotFoundException(Long id) {
        this(String.format("Cut style with ID %s not found.", id));
    }

    public CutStyleNotFoundException(String message) {
        super(message);
    }
}
