package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.model.Ingredient;
import pzinsta.repository.IngredientRepository;

import java.util.Optional;

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
    public Optional<Ingredient> findById(@PathVariable("id") Long id) {
        return ingredientRepository.findById(id);
    }
}
