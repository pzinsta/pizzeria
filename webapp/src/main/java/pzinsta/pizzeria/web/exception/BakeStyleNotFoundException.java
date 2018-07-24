package pzinsta.pizzeria.web.exception;

public class BakeStyleNotFoundException extends RuntimeException {
    public BakeStyleNotFoundException(Long id) {
        this(String.format("Bake style with ID %s not found.", id));
    }

    public BakeStyleNotFoundException(String message) {
        super(message);
    }
}
