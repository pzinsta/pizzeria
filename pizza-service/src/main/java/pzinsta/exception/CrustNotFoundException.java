package pzinsta.exception;

public class CrustNotFoundException extends RuntimeException {
    public CrustNotFoundException(Long id) {
        this(String.format("Crust with ID %s not found.", id));
    }

    public CrustNotFoundException(String message) {
        super(message);
    }
}
