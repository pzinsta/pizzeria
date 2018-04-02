package pzinsta.pizzeria.web.util;

import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.IngredientType;
import pzinsta.pizzeria.service.dto.PizzaOrderDTO;
import pzinsta.pizzeria.web.form.PizzaBuilderForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientSide.LEFT;
import static pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientSide.RIGHT;

public class Utils {
    private Utils() {}

    public static PizzaOrderDTO createPizzaOrderDTO(PizzaBuilderForm pizzaBuilderForm) {
        PizzaOrderDTO pizzaOrderDTO = new PizzaOrderDTO();
        pizzaOrderDTO.setBakeStyleId(pizzaBuilderForm.getBakeStyleId());
        pizzaOrderDTO.setCrustId(pizzaBuilderForm.getCrustId());
        pizzaOrderDTO.setCutStyleId(pizzaBuilderForm.getCutStyleId());
        pizzaOrderDTO.setPizzaSizeId(pizzaBuilderForm.getPizzaSizeId());
        pizzaOrderDTO.setQuantity(pizzaBuilderForm.getQuantity());
        pizzaOrderDTO.setLeftSideIngredientIdByQuantity(getIngredientsByQuantity(pizzaBuilderForm, LEFT));
        pizzaOrderDTO.setRightSideIngredientIdByQuantity(getIngredientsByQuantity(pizzaBuilderForm, RIGHT));
        return pizzaOrderDTO;
    }

    private static Map<Long, Integer> getIngredientsByQuantity(PizzaBuilderForm pizzaBuilderForm, PizzaBuilderForm.IngredientSide ingredientSide) {
        return pizzaBuilderForm.getIngredientGroups().stream().flatMap(ingredientGroup -> ingredientGroup.getIngredientQuantities().stream())
                .filter(ingredientQuantity -> belongsToIngredientSide(ingredientSide, ingredientQuantity)).collect(Collectors.toMap(o -> o.getIngredient().getId(), Utils::getQuantity));
    }

    private static int getQuantity(PizzaBuilderForm.IngredientQuantity ingredientQuantity) {
        return ingredientQuantity.isX2() ? 2 : 1;
    }

    private static boolean belongsToIngredientSide(PizzaBuilderForm.IngredientSide ingredientSide, PizzaBuilderForm.IngredientQuantity ingredientQuantity) {
        return ingredientQuantity.getIngredientSide() == ingredientSide || ingredientQuantity.getIngredientSide() == PizzaBuilderForm.IngredientSide.WHOLE;
    }

    public static List<PizzaBuilderForm.IngredientGroup> generateIngredientGroups(Collection<Ingredient> ingredients) {
        Stream<PizzaBuilderForm.IngredientQuantity> ingredientQuantityStream = ingredients.stream().map(Utils::createIngredientQuantity);
        Map<IngredientType, List<PizzaBuilderForm.IngredientQuantity>> ingredientQuantityByIngredientType = ingredientQuantityStream.collect(Collectors.groupingBy(ingredientQuantity -> ingredientQuantity.getIngredient().getIngredientType()));
        return ingredientQuantityByIngredientType.entrySet().stream().collect(ArrayList::new, (objects, ingredientTypeListEntry) -> {
            PizzaBuilderForm.IngredientGroup ingredientGroup = new PizzaBuilderForm.IngredientGroup();
            ingredientGroup.setIngredientType(ingredientTypeListEntry.getKey());
            ingredientGroup.setIngredientQuantities(ingredientTypeListEntry.getValue());
            objects.add(ingredientGroup);
        }, ArrayList::addAll);
    }

    private static PizzaBuilderForm.IngredientQuantity createIngredientQuantity(Ingredient ingredient) {
        PizzaBuilderForm.IngredientQuantity ingredientQuantity = new PizzaBuilderForm.IngredientQuantity();
        ingredientQuantity.setIngredient(ingredient);
        return ingredientQuantity;
    }

}
