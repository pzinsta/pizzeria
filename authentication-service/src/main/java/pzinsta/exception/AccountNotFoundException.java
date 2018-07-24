package pzinsta.exception;

public class AccountNotFoundException extends RuntimeException {

    public static AccountNotFoundException withId(Long id) {
        return new AccountNotFoundException(String.format("Account with ID %s not found", id));
    }

    public static AccountNotFoundException withUsername(String username) {
        return new AccountNotFoundException(String.format("Account with username %s not found.", username));
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
