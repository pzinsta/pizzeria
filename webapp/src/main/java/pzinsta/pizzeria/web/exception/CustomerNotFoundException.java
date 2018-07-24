package pzinsta.pizzeria.web.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        this(String.format("Customer with ID %s not found.", id));
    }
    public CustomerNotFoundException(String message) {
        super(message);
    }

    public static CustomerNotFoundException withUsername(String username) {
        return new CustomerNotFoundException(String.format("Customer with username %s not found.", username));
    }
}
