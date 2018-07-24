package pzinsta.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        this(String.format("Customer with ID %s not found.", id));
    }
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
