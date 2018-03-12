package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.IngredientType;
import pzinsta.pizzeria.service.PizzaBuilderService;
import pzinsta.pizzeria.service.dto.PizzaOrderDTO;
import pzinsta.pizzeria.web.form.PizzaBuilderForm;
import pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientGroup;
import pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientQuantity;
import pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientSide;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientSide.LEFT;
import static pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientSide.RIGHT;

@Controller
@RequestMapping("/builder")
public class PizzaBuilderController {

    private PizzaBuilderService pizzaBuilderService;

    @Autowired
    public PizzaBuilderController(PizzaBuilderService pizzaBuilderService) {
        this.pizzaBuilderService = pizzaBuilderService;
    }

    @GetMapping
    public String showPizzaBuilderForm(Model model) {
        PizzaBuilderForm pizzaBuilderForm = new PizzaBuilderForm();
        pizzaBuilderForm.setIngredientGroups(generateIngredientGroups(pizzaBuilderService.getIngredients()));
        model.addAttribute("pizzaBuilderForm", pizzaBuilderForm);
        model.addAttribute("crusts", pizzaBuilderService.getCrusts());
        model.addAttribute("pizzaSizes", pizzaBuilderService.getPizzaSizes());
        model.addAttribute("bakeStyles", pizzaBuilderService.getBakeStyles());
        model.addAttribute("cutStyles", pizzaBuilderService.getCutStyles());
        model.addAttribute("quantities", pizzaBuilderService.getQuantities());
        return "pizzaBuilder";
    }

    @PostMapping
    public String processPizzaBuilderForm(@Valid @ModelAttribute PizzaBuilderForm pizzaBuilderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pizzaBuilder";
        }

        pizzaBuilderService.addOrderItemToOrder(createPizzaOrderDTO(pizzaBuilderForm));

        return "redirect:/";
    }

    private List<IngredientGroup> generateIngredientGroups(Collection<Ingredient> ingredients) {
        Stream<IngredientQuantity> ingredientQuantityStream = ingredients.stream().map(this::createIngredientQuantity);
        Map<IngredientType, List<IngredientQuantity>> ingredientQuantityByIngredientType = ingredientQuantityStream.collect(Collectors.groupingBy(ingredientQuantity -> ingredientQuantity.getIngredient().getIngredientType()));
        return ingredientQuantityByIngredientType.entrySet().stream().collect(ArrayList::new, (objects, ingredientTypeListEntry) -> {
            IngredientGroup ingredientGroup = new IngredientGroup();
            ingredientGroup.setIngredientType(ingredientTypeListEntry.getKey());
            ingredientGroup.setIngredientQuantities(ingredientTypeListEntry.getValue());
            objects.add(ingredientGroup);
        }, ArrayList::addAll);
    }

    private IngredientQuantity createIngredientQuantity(Ingredient ingredient) {
        IngredientQuantity ingredientQuantity = new IngredientQuantity();
        ingredientQuantity.setIngredient(ingredient);
        return ingredientQuantity;
    }

    private PizzaOrderDTO createPizzaOrderDTO(PizzaBuilderForm pizzaBuilderForm) {
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

    private Map<Long, Integer> getIngredientsByQuantity(PizzaBuilderForm pizzaBuilderForm, IngredientSide ingredientSide) {
        return pizzaBuilderForm.getIngredientGroups().stream().flatMap(ingredientGroup -> ingredientGroup.getIngredientQuantities().stream())
                    .filter(ingredientQuantity -> belongsToIngredientSide(ingredientSide, ingredientQuantity)).collect(Collectors.toMap(o -> o.getIngredient().getId(), this::getQuantity));
    }

    private int getQuantity(IngredientQuantity ingredientQuantity) {
        return ingredientQuantity.isX2() ? 2 : 1;
    }

    private boolean belongsToIngredientSide(IngredientSide ingredientSide, IngredientQuantity ingredientQuantity) {
        return ingredientQuantity.getIngredientSide() == ingredientSide || ingredientQuantity.getIngredientSide() == IngredientSide.WHOLE;
    }

    public PizzaBuilderService getPizzaBuilderService() {
        return pizzaBuilderService;
    }

    public void setPizzaBuilderService(PizzaBuilderService pizzaBuilderService) {
        this.pizzaBuilderService = pizzaBuilderService;
    }
}
