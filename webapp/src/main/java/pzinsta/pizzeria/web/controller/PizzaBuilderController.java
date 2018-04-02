package pzinsta.pizzeria.web.controller;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.web.form.PizzaBuilderForm;

import javax.validation.Valid;

import static pzinsta.pizzeria.web.util.Utils.createPizzaOrderDTO;
import static pzinsta.pizzeria.web.util.Utils.generateIngredientGroups;

@Controller
@RequestMapping("/builder")
public class PizzaBuilderController {

    private OrderService orderService;

    @Autowired
    public PizzaBuilderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showPizzaBuilderForm(Model model) {
        PizzaBuilderForm pizzaBuilderForm = new PizzaBuilderForm();
        pizzaBuilderForm.setIngredientGroups(generateIngredientGroups(orderService.getIngredients()));
        model.addAttribute("pizzaBuilderForm", pizzaBuilderForm);
        model.addAttribute("crusts", orderService.getCrusts());
        model.addAttribute("pizzaSizes", orderService.getPizzaSizes());
        model.addAttribute("bakeStyles", orderService.getBakeStyles());
        model.addAttribute("cutStyles", orderService.getCutStyles());
        model.addAttribute("quantities", orderService.getQuantities());
        return "pizzaBuilder";
    }

    @PostMapping
    public String processPizzaBuilderForm(@Valid @ModelAttribute PizzaBuilderForm pizzaBuilderForm, BindingResult bindingResult, @RequestParam(value = "redirectTo", defaultValue = "/") String redirectTo) {
        if (bindingResult.hasErrors()) {
            return "pizzaBuilder";
        }

        orderService.addOrderItemToCart(createPizzaOrderDTO(pizzaBuilderForm));

        return Joiner.on(StringUtils.EMPTY).join("redirect:", redirectTo);
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
