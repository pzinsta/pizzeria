package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.web.client.PizzaServiceClient;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaTemplate;
import pzinsta.pizzeria.web.model.CartDTO;
import pzinsta.pizzeria.web.service.impl.OrderService;

import java.util.Collection;

@Controller
@RequestMapping("/")
public class HomeController {
    private OrderService orderService;
    private PizzaServiceClient pizzaServiceClient;

    @Autowired
    public HomeController(OrderService orderService, PizzaServiceClient pizzaServiceClient) {
        this.orderService = orderService;
        this.pizzaServiceClient = pizzaServiceClient;
    }

    @ModelAttribute("pizzaTemplates")
    public Collection<PizzaTemplate> pizzaTemplates() {
        return pizzaServiceClient.findAllPizzaTemplates();
    }

    @ModelAttribute("cart")
    public CartDTO cart() {
        return orderService.getCartDTO();
    }

    @GetMapping
    public String home(Model model) {
        return "home";
    }

    public PizzaServiceClient getPizzaServiceClient() {
        return pizzaServiceClient;
    }

    public void setPizzaServiceClient(PizzaServiceClient pizzaServiceClient) {
        this.pizzaServiceClient = pizzaServiceClient;
    }
}
