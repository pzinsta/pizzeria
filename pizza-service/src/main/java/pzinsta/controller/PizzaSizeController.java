package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.PizzaSizeNotFoundException;
import pzinsta.model.PizzaSize;
import pzinsta.repository.PizzaSizeRepository;

@RestController
@RequestMapping("/pizzaSizes")
public class PizzaSizeController {
    private PizzaSizeRepository pizzaSizeRepository;

    @Autowired
    public PizzaSizeController(PizzaSizeRepository pizzaSizeRepository) {
        this.pizzaSizeRepository = pizzaSizeRepository;
    }

    @GetMapping
    public Iterable<PizzaSize> findAll() {
        return pizzaSizeRepository.findAll();
    }

    @GetMapping("/{id}")
    public PizzaSize findById(@PathVariable("id") Long id) {
        return pizzaSizeRepository.findById(id)
                .orElseThrow(() -> new PizzaSizeNotFoundException(id));
    }

    @ExceptionHandler(PizzaSizeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePizzaSizeNotFoundException(PizzaSizeNotFoundException exception) {
        return exception.getMessage();
    }
}
