package pzinsta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pzinsta.exception.PizzaNotFoundException;
import pzinsta.model.Pizza;
import pzinsta.model.PizzaSide;
import pzinsta.repository.PizzaRepository;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

@Service
public class PizzaService {

    private static final MonetaryAmount ZERO = Monetary.getDefaultAmountFactory().setNumber(0).setCurrency("USD").create();

    private PizzaRepository pizzaRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public Iterable<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    public Pizza findById(Long id) {
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new PizzaNotFoundException(id));
    }

    public Pizza save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public MonetaryAmount calculateCost(Pizza pizza) {
        MonetaryAmount crustCost = pizza.getCrust().getPrice();
        MonetaryAmount pizzaSizeCost = pizza.getSize().getPrice();

        double ingredientCostFactor = pizza.getSize().getIngredientCostFactor();
        MonetaryAmount leftPizzaSideIngredientsCost = calculatePizzaSideIngredientsCost(pizza.getLeftPizzaSide()).multiply(ingredientCostFactor);
        MonetaryAmount rightPizzaSideIngredientsCost = calculatePizzaSideIngredientsCost(pizza.getRightPizzaSide()).multiply(ingredientCostFactor);

        return crustCost.add(pizzaSizeCost).add(leftPizzaSideIngredientsCost).add(rightPizzaSideIngredientsCost);
    }

    public MonetaryAmount calculateCostById(Long pizzaId) {
        return pizzaRepository.findById(pizzaId)
                .map(this::calculateCost)
                .orElseThrow(() -> new PizzaNotFoundException(pizzaId));
    }

    private static MonetaryAmount calculatePizzaSideIngredientsCost(PizzaSide pizzaSide) {
        return pizzaSide.getPizzaItems().stream()
                .map(pizzaItem -> pizzaItem.getIngredient().getPrice().multiply(pizzaItem.getQuantity()))
                .reduce(ZERO, MonetaryAmount::add);
    }

}
