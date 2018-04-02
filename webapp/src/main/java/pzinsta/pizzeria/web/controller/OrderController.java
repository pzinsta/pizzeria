package pzinsta.pizzeria.web.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.dto.PizzaOrderDTO;
import pzinsta.pizzeria.web.form.PizzaBuilderForm;
import pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientGroup;

import javax.validation.Valid;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientSide.LEFT;
import static pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientSide.RIGHT;
import static pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientSide.WHOLE;
import static pzinsta.pizzeria.web.util.Utils.createPizzaOrderDTO;
import static pzinsta.pizzeria.web.util.Utils.generateIngredientGroups;

@Controller
@RequestMapping("/orderItem")
public class OrderController {

    private static final int DOUBLE = 2;

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/remove/{orderItemId}")
    public String removeOrderItem(@PathVariable("orderItemId") int orderItemIndex, @RequestParam(value = "redirectTo", defaultValue = "/") String redirectTo) {
        orderService.removeOrderItem(orderItemIndex);
        return Joiner.on(EMPTY).join("redirect:", redirectTo);
    }

    @GetMapping("/clear")
    public String clear(@RequestParam(value = "redirectTo", defaultValue = "/") String redirectTo) {
        orderService.emptyCart();
        return Joiner.on(EMPTY).join("redirect:", redirectTo);
    }

    @GetMapping("/edit/{orderItemId}")
    public String showEditOrderItemForm(@PathVariable("orderItemId") int orderItemIndex, Model model) {

        model.addAttribute("crusts", orderService.getCrusts());
        model.addAttribute("pizzaSizes", orderService.getPizzaSizes());
        model.addAttribute("bakeStyles", orderService.getBakeStyles());
        model.addAttribute("cutStyles", orderService.getCutStyles());
        model.addAttribute("quantities", orderService.getQuantities());
        model.addAttribute("pizzaBuilderForm", createPizzaBuilderForm(orderService.getPizzaOrderDTOByOrderItemId(orderItemIndex)));

        return "editOrderItem";
    }

    @PostMapping("/edit/{orderItemId}")
    public String editOrderItem(@PathVariable("orderItemId") int orderItemIndex, @ModelAttribute @Valid PizzaBuilderForm pizzaBuilderForm, @RequestParam(value = "redirectTo", defaultValue = "/") String redirectTo) {
        orderService.replaceOrderItem(orderItemIndex, createPizzaOrderDTO(pizzaBuilderForm));
        return Joiner.on(EMPTY).join("redirect:", redirectTo);
    }

    private PizzaBuilderForm createPizzaBuilderForm(PizzaOrderDTO pizzaOrderDTO) {
        PizzaBuilderForm pizzaBuilderForm = new PizzaBuilderForm();

        pizzaBuilderForm.setBakeStyleId(pizzaOrderDTO.getBakeStyleId());
        pizzaBuilderForm.setCrustId(pizzaOrderDTO.getCrustId());
        pizzaBuilderForm.setCutStyleId(pizzaOrderDTO.getCutStyleId());
        pizzaBuilderForm.setPizzaSizeId(pizzaOrderDTO.getPizzaSizeId());
        pizzaBuilderForm.setQuantity(pizzaOrderDTO.getQuantity());
        pizzaBuilderForm.setIngredientGroups(getIngredientGroups(pizzaOrderDTO));

        return pizzaBuilderForm;
    }

    private List<IngredientGroup> getIngredientGroups(PizzaOrderDTO pizzaOrderDTO) {
        Set<Long> leftSideIngredientIds = pizzaOrderDTO.getLeftSideIngredientIdByQuantity().keySet();
        Set<Long> rightSideIngredientIds = pizzaOrderDTO.getRightSideIngredientIdByQuantity().keySet();
        Set<Long> wholeIngredientIds = Sets.intersection(leftSideIngredientIds, rightSideIngredientIds);

        Stream<Entry<Long, Integer>> leftSideIngredientsByQuantityStream = pizzaOrderDTO.getLeftSideIngredientIdByQuantity().entrySet().stream();
        Stream<Entry<Long, Integer>> rightSideIngredientByQuantityStream = pizzaOrderDTO.getRightSideIngredientIdByQuantity().entrySet().stream();
        Set<Long> ingredientIdsWithDoubleQuantity = Stream.concat(leftSideIngredientsByQuantityStream, rightSideIngredientByQuantityStream)
                .filter(entry -> entry.getValue() == DOUBLE).map(Entry::getKey).collect(Collectors.toSet());

        List<IngredientGroup> ingredientGroups = generateIngredientGroups(orderService.getIngredients());

        ingredientGroups.stream().flatMap(ingredientGroup -> ingredientGroup.getIngredientQuantities().stream())
                .filter(ingredientQuantity -> leftSideIngredientIds.contains(ingredientQuantity.getIngredient().getId()))
                .forEach(ingredientQuantity -> ingredientQuantity.setIngredientSide(LEFT));

        ingredientGroups.stream().flatMap(ingredientGroup -> ingredientGroup.getIngredientQuantities().stream())
                .filter(ingredientQuantity -> rightSideIngredientIds.contains(ingredientQuantity.getIngredient().getId()))
                .forEach(ingredientQuantity -> ingredientQuantity.setIngredientSide(RIGHT));

        ingredientGroups.stream().flatMap(ingredientGroup -> ingredientGroup.getIngredientQuantities().stream())
                .filter(ingredientQuantity -> wholeIngredientIds.contains(ingredientQuantity.getIngredient().getId()))
                .forEach(ingredientQuantity -> ingredientQuantity.setIngredientSide(WHOLE));

        ingredientGroups.stream().flatMap(ingredientGroup -> ingredientGroup.getIngredientQuantities().stream())
                .filter(ingredientQuantity -> ingredientIdsWithDoubleQuantity.contains(ingredientQuantity.getIngredient().getId()))
                .forEach(ingredientQuantity -> ingredientQuantity.setX2(true));

        return ingredientGroups;
    }
}
