package pzinsta.pizzeria.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import pzinsta.pizzeria.dao.PizzaDao;
import pzinsta.pizzeria.model.pizza.BakeStyle;
import pzinsta.pizzeria.model.pizza.Crust;
import pzinsta.pizzeria.model.pizza.CutStyle;
import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.IngredientType;
import pzinsta.pizzeria.model.pizza.Pizza;
import pzinsta.pizzeria.model.pizza.PizzaItem;
import pzinsta.pizzeria.model.pizza.PizzaSide;
import pzinsta.pizzeria.model.pizza.PizzaSize;
import pzinsta.pizzeria.service.PizzaService;

public class PizzaServiceImpl implements PizzaService {

	private PizzaDao pizzaDao;
	
	public PizzaServiceImpl(PizzaDao pizzaDao) {
		this.pizzaDao = pizzaDao;
	}
	
	@Override
	public Set<Crust> getCrusts() {
		return pizzaDao.getCrusts();
	}

	@Override
	public Set<BakeStyle> getBakeStyles() {
		return pizzaDao.getBakeStyles();
	}

	@Override
	public Set<CutStyle> getCutStyles() {
		return pizzaDao.getCutStyles();
	}

	@Override
	public Set<IngredientType> getIngredientTypes() {
		return pizzaDao.getIngredientTypes();
	}

	@Override
	public Set<Ingredient> getIngredients() {
		return pizzaDao.getIngredients();
	}

	@Override
	public Set<PizzaSize> getPizzaSizes() {
		return pizzaDao.getPizzaSizes();
	}

	@Override
	public Pizza buildPizza(long crustId, long pizzaSizeId, int quantity, long bakeStyleId, long cutStyleId,
			Map<String, String[]> ingredientsParametersMap) {
		Pizza pizza = new Pizza();
		pizza.setCrust(pizzaDao.getCrustById(crustId).get());
		pizza.setSize(pizzaDao.getPizzaSizeById(pizzaSizeId).get());
		pizza.setBakeStyle(pizzaDao.getBakeStyleById(bakeStyleId).get());
		pizza.setCutStyle(pizzaDao.getCutStyleById(cutStyleId).get());
		
		PizzaSide leftSide = new PizzaSide();
		leftSide.setItems(extractIngredientItemsForLeftSide(ingredientsParametersMap));
		pizza.setLeft(leftSide);
		
		PizzaSide rightSide = new PizzaSide();
		rightSide.setItems(extractIngredientItemsForRightSide(ingredientsParametersMap));
		pizza.setRight(rightSide);
		
		return pizza;
	}

	private Collection<PizzaItem> extractIngredientItemsForRightSide(Map<String, String[]> ingredientsParametersMap) {
		Collection<PizzaItem> pizzaItems = new ArrayList<>();
		ImmutableMap<Long, Ingredient> ingredientsMap = Maps.uniqueIndex(pizzaDao.getIngredients(), Ingredient::getId);
		for(Map.Entry<String, String[]> entry : ingredientsParametersMap.entrySet()) {
		 	String ingredientIdString = FluentIterable.from(Splitter.on(":").limit(2).trimResults().omitEmptyStrings().split(entry.getKey())).last().get();
		 	String[] values = entry.getValue();
		 	boolean anyMatch = Stream.of(values).anyMatch(Predicates.equalTo("right").or(Predicates.equalTo("whole")));
		 	if (anyMatch) {
		 		long ingredientId = Long.parseLong(ingredientIdString);
		 		PizzaItem pizzaItem = new PizzaItem();
		 		Ingredient ingredient = ingredientsMap.get(ingredientId);
		 		pizzaItem.setIngredient(ingredient);
		 		if (Stream.of(values).anyMatch("double"::equals)) {
		 			pizzaItem.setQuantity(2);
		 		}
		 		else {
		 			pizzaItem.setQuantity(1);
		 		}
		 		pizzaItems.add(pizzaItem);
		 	}
		}
		return pizzaItems;
	}

	// TODO: copypaste
	private Collection<PizzaItem> extractIngredientItemsForLeftSide(Map<String, String[]> ingredientsParametersMap) {
		Collection<PizzaItem> pizzaItems = new ArrayList<>();
		ImmutableMap<Long, Ingredient> ingredientsMap = Maps.uniqueIndex(pizzaDao.getIngredients(), Ingredient::getId);
		for(Map.Entry<String, String[]> entry : ingredientsParametersMap.entrySet()) {
		 	String ingredientIdString = FluentIterable.from(Splitter.on(":").limit(2).trimResults().omitEmptyStrings().split(entry.getKey())).last().get();
		 	String[] values = entry.getValue();
		 	boolean anyMatch = Stream.of(values).anyMatch(Predicates.equalTo("left").or(Predicates.equalTo("whole")));
		 	if (anyMatch) {
		 		long ingredientId = Long.parseLong(ingredientIdString);
		 		PizzaItem pizzaItem = new PizzaItem();
		 		Ingredient ingredient = ingredientsMap.get(ingredientId);
		 		pizzaItem.setIngredient(ingredient);
		 		if (Stream.of(values).anyMatch("double"::equals)) {
		 			pizzaItem.setQuantity(2);
		 		}
		 		else {
		 			pizzaItem.setQuantity(1);
		 		}
		 		pizzaItems.add(pizzaItem);
		 	}
		}
		return pizzaItems;
	}
	
	@Override
	public void savePizza(Pizza pizza) {
		pizzaDao.savePizza(pizza);
	}

	@Override
	public Pizza getPizza(Long id) {
		return pizzaDao.getPizza(id);
	}

}
