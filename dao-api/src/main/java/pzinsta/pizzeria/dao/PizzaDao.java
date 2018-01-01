package pzinsta.pizzeria.dao;

import java.util.Optional;
import java.util.Set;

import pzinsta.pizzeria.model.pizza.BakeStyle;
import pzinsta.pizzeria.model.pizza.Crust;
import pzinsta.pizzeria.model.pizza.CutStyle;
import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.IngredientType;
import pzinsta.pizzeria.model.pizza.Pizza;
import pzinsta.pizzeria.model.pizza.PizzaSize;

public interface PizzaDao {

	Set<Crust> getCrusts();

	Set<BakeStyle> getBakeStyles();

	Set<CutStyle> getCutStyles();

	Set<IngredientType> getIngredientTypes();

	Set<Ingredient> getIngredients();

	Set<PizzaSize> getPizzaSizes();

	Optional<Crust> getCrustById(long crustId);

	Optional<PizzaSize> getPizzaSizeById(long pizzaSizeId);

	Optional<BakeStyle> getBakeStyleById(long bakeStyleId);

	Optional<CutStyle> getCutStyleById(long cutStyleId);

	void savePizza(Pizza pizza);

	Pizza getPizza(Long id);
	
}
