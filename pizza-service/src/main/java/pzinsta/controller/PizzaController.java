package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.PizzaNotFoundException;
import pzinsta.model.Pizza;
import pzinsta.service.PizzaService;

import javax.money.MonetaryAmount;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    private PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public Iterable<Pizza> findAll() {
        return pizzaService.findAll();
    }

    @GetMapping("/{id}")
    public Pizza findById(@PathVariable("id") Long id) {
        return pizzaService.findById(id);
    }

    @GetMapping("/{id}/cost")
    public MonetaryAmount calculateCost(@PathVariable("id") Long id) {
        return pizzaService.calculateCostById(id);
    }

    @PostMapping("/cost")
    public MonetaryAmount calculateCost(@RequestBody Pizza pizza) {
        return pizzaService.calculateCost(pizza);
    }

    @PostMapping
    public Pizza save(@RequestBody Pizza pizza) {
        return pizzaService.save(pizza);
    }

    @ExceptionHandler(PizzaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePizzaNotFoundException(PizzaNotFoundException exception) {
        return exception.getMessage();
    }

    public PizzaService getPizzaService() {
        return pizzaService;
    }

    public void setPizzaService(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }
}
