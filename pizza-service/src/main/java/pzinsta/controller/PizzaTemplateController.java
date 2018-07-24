package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.PizzaTemplateNotFoundException;
import pzinsta.model.PizzaTemplate;
import pzinsta.repository.PizzaTemplateRepository;

@RestController
@RequestMapping("/pizzaTemplates")
public class PizzaTemplateController {
    private PizzaTemplateRepository pizzaTemplateRepository;

    @Autowired
    public PizzaTemplateController(PizzaTemplateRepository pizzaTemplateRepository) {
        this.pizzaTemplateRepository = pizzaTemplateRepository;
    }

    @GetMapping
    public Iterable<PizzaTemplate> findAll() {
        return pizzaTemplateRepository.findAll();
    }

    @GetMapping("/{id}")
    public PizzaTemplate findById(@PathVariable("id") Long id) {
        return pizzaTemplateRepository.findById(id)
                .orElseThrow(() -> new PizzaTemplateNotFoundException(id));
    }

    @ExceptionHandler(PizzaTemplateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePizzaTemplateNotFoundException(PizzaTemplateNotFoundException exception) {
        return exception.getMessage();
    }
}
