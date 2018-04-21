package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.model.order.OrderItemTemplate;
import pzinsta.pizzeria.model.user.Customer;
import pzinsta.pizzeria.service.CustomerService;
import pzinsta.pizzeria.service.OrderItemTemplateService;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private CustomerService customerService;
    private OrderItemTemplateService orderItemTemplateService;

    @Autowired
    public HomeController(CustomerService customerService, OrderItemTemplateService orderItemTemplateService) {
        this.customerService = customerService;
        this.orderItemTemplateService = orderItemTemplateService;
    }

    @ModelAttribute("orderItemTemplates")
    public Collection<OrderItemTemplate> orderItemTemplates() {
        return  orderItemTemplateService.getOrderItemTemplates();
    }

    @GetMapping
    public String home(Model model, Principal principal) {
        // TODO: 4/2/2018
        if (principal == null) {
            return "home";
        }
        Optional<Customer> customerOptional = customerService.getCustomerByUsername(principal.getName());
        customerOptional.ifPresent(customer -> model.addAttribute("customer", customer));
        return "home";
    }

    public OrderItemTemplateService getOrderItemTemplateService() {
        return orderItemTemplateService;
    }

    public void setOrderItemTemplateService(OrderItemTemplateService orderItemTemplateService) {
        this.orderItemTemplateService = orderItemTemplateService;
    }
}
