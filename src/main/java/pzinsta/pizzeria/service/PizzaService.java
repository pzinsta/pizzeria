package pzinsta.pizzeria.service;

import java.util.Map;
import java.util.Set;

import pzinsta.pizzeria.model.pizza.BakeStyle;
import pzinsta.pizzeria.model.pizza.Crust;
import pzinsta.pizzeria.model.pizza.CutStyle;
import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.IngredientType;
import pzinsta.pizzeria.model.pizza.Pizza;
import pzinsta.pizzeria.model.pizza.PizzaSize;

public interface PizzaService {
	Set<Crust> getCrusts();
	Set<BakeStyle> getBakeStyles();
	Set<CutStyle> getCutStyles();
	Set<IngredientType> getIngredientTypes();
	Set<Ingredient> getIngredients();
	Set<PizzaSize> getPizzaSizes();
	Pizza buildPizza(long crustId, long pizzaSizeId, int quantity, long bakeStyleId, long cutStyleId,
			Map<String, String[]> ingredientsParametersMap);
	void savePizza(Pizza pizza);

}
