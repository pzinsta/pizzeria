package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.model.order.PizzaTemplate;
import pzinsta.pizzeria.service.PizzaTemplateService;
import pzinsta.pizzeria.web.client.PizzaServiceClient;
import pzinsta.pizzeria.web.model.CartDTO;
import pzinsta.pizzeria.web.service.OrderServiceImpl;

import java.util.Collection;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private OrderServiceImpl orderService;
    private PizzaTemplateService pizzaTemplateService;
    private PizzaServiceClient pizzaServiceClient;

    @Autowired
    public HomeController(PizzaTemplateService pizzaTemplateService, PizzaServiceClient pizzaServiceClient) {
        this.pizzaTemplateService = pizzaTemplateService;
        this.pizzaServiceClient = pizzaServiceClient;
    }

    @ModelAttribute("pizzaTemplates")
    public Collection<PizzaTemplate> pizzaTemplates() {
        return pizzaTemplateService.getOrderItemTemplates();
    }

    @ModelAttribute("cart")
    public CartDTO cart() {
        return orderService.getCartDTO();
    }

    @GetMapping
    public String home(Model model) {
        return "home";
    }

    public PizzaTemplateService getPizzaTemplateService() {
        return pizzaTemplateService;
    }

    public void setPizzaTemplateService(PizzaTemplateService pizzaTemplateService) {
        this.pizzaTemplateService = pizzaTemplateService;
    }

    public PizzaServiceClient getPizzaServiceClient() {
        return pizzaServiceClient;
    }

    public void setPizzaServiceClient(PizzaServiceClient pizzaServiceClient) {
        this.pizzaServiceClient = pizzaServiceClient;
    }
}
