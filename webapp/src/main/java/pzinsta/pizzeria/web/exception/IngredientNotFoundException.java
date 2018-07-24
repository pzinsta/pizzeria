package pzinsta.pizzeria.web.exception;

public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException(Long id) {
        this(String.format("Ingredient with ID %s not found.", id));
    }

    public IngredientNotFoundException(String message) {
        super(message);
    }
}
