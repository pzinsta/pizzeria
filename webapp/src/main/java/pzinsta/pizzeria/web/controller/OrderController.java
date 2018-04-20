package pzinsta.pizzeria.web.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pzinsta.pizzeria.model.pizza.BakeStyle;
import pzinsta.pizzeria.model.pizza.Crust;
import pzinsta.pizzeria.model.pizza.CutStyle;
import pzinsta.pizzeria.model.pizza.PizzaSize;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.dto.PizzaOrderDTO;
import pzinsta.pizzeria.web.form.PizzaBuilderForm;
import pzinsta.pizzeria.web.form.PizzaBuilderForm.IngredientGroup;
import pzinsta.pizzeria.web.validator.PizzaBuilderFormValidator;

import javax.validation.Valid;
import java.util.Collection;
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
@RequestMapping("/order/orderItem")
public class OrderController {

    private static final int DOUBLE = 2;

    private OrderService orderService;
    private PizzaBuilderFormValidator pizzaBuilderFormValidator;

    @Autowired
    public OrderController(OrderService orderService, PizzaBuilderFormValidator pizzaBuilderFormValidator) {
        this.orderService = orderService;
        this.pizzaBuilderFormValidator = pizzaBuilderFormValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(pizzaBuilderFormValidator);
    }

    @ModelAttribute("crusts")
    public Collection<Crust> crusts() {
        return orderService.getCrusts();
    }

    @ModelAttribute("pizzaSizes")
    public Collection<PizzaSize> pizzaSizes() {
        return orderService.getPizzaSizes();
    }

    @ModelAttribute("bakeStyles")
    public Collection<BakeStyle> bakeStyles() {
        return orderService.getBakeStyles();
    }

    @ModelAttribute("cutStyles")
    public Collection<CutStyle> cutStyles() {
        return orderService.getCutStyles();
    }

    @ModelAttribute("quantities")
    public Collection<Integer> quantities() {
        return orderService.getQuantities();
    }

    @GetMapping("/{orderItemId}/remove}")
    public String removeOrderItem(@PathVariable("orderItemId") int orderItemIndex, @RequestParam(value = "redirectTo", defaultValue = "/") String redirectTo) {
        orderService.removeOrderItem(orderItemIndex);
        return Joiner.on(EMPTY).join("redirect:", redirectTo);
    }

    @GetMapping("/clear")
    public String clear(@RequestParam(value = "redirectTo", defaultValue = "/") String redirectTo) {
        orderService.emptyCart();
        return Joiner.on(EMPTY).join("redirect:", redirectTo);
    }

    @GetMapping("/{orderItemId}/edit")
    public String showEditOrderItemForm(@PathVariable("orderItemId") int orderItemIndex, Model model) {

        model.addAttribute("pizzaBuilderForm", createPizzaBuilderForm(orderService.getPizzaOrderDTOByOrderItemId(orderItemIndex)));

        return "editOrderItem";
    }

    @PostMapping("/{orderItemId}/edit")
    public String editOrderItem(@PathVariable("orderItemId") int orderItemIndex, @ModelAttribute @Valid PizzaBuilderForm pizzaBuilderForm, BindingResult bindingResult, @RequestParam(value = "redirectTo", defaultValue = "/") String redirectTo, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
           return "editOrderItem";
        }

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
