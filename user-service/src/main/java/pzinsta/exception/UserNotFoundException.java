package pzinsta.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        this(String.format("User with ID %s not found.", id));
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException withUsername(String username) {
        return new UserNotFoundException(String.format("User with username %s not found.", username));
    }
}
