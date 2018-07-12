package pzinsta.exception;

public class PizzaNotFoundException extends RuntimeException {
    public PizzaNotFoundException(Long id) {
        super(String.format("Pizza with ID %s not found.", id));
    }
}
