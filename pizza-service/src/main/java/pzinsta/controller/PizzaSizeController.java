package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.model.PizzaSize;
import pzinsta.repository.PizzaSizeRepository;

import java.util.Optional;

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
    public Optional<PizzaSize> findById(@PathVariable("id") Long id) {
        return pizzaSizeRepository.findById(id);
    }
}
