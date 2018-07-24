package pzinsta.pizzeria.web.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        this(String.format("User with ID %s not found.", id));
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
