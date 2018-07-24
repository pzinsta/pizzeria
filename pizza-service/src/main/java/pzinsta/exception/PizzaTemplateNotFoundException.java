package pzinsta.exception;

public class PizzaTemplateNotFoundException extends RuntimeException {
    public PizzaTemplateNotFoundException(Long id) {
        this(String.format("Pizza template with ID %s not found.", id));
    }

    public PizzaTemplateNotFoundException(String message) {
        super(message);
    }
}
