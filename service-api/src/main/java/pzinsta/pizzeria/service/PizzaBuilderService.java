package pzinsta.pizzeria.service;

import pzinsta.pizzeria.model.pizza.BakeStyle;
import pzinsta.pizzeria.model.pizza.Crust;
import pzinsta.pizzeria.model.pizza.CutStyle;
import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.PizzaSize;
import pzinsta.pizzeria.service.dto.PizzaOrderDTO;

import java.util.Collection;

public interface PizzaBuilderService {

    Collection<Crust> getCrusts();

    Collection<PizzaSize> getPizzaSizes();

    Collection<BakeStyle> getBakeStyles();

    Collection<CutStyle> getCutStyles();

    Collection<Ingredient> getIngredients();

    Collection<Integer> getQuantities();

    void addOrderItemToOrder(PizzaOrderDTO pizzaOrderDTO);
}
