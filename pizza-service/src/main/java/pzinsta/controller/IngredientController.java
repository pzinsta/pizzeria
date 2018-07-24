package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.IngredientNotFoundException;
import pzinsta.model.Ingredient;
import pzinsta.repository.IngredientRepository;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ingredient findById(@PathVariable("id") Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIngredientNotFoundException(IngredientNotFoundException exception) {
        return exception.getMessage();
    }
}
