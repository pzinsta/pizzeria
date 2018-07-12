package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.user.Customer;
import pzinsta.pizzeria.service.CustomerService;
import pzinsta.pizzeria.service.exception.CustomerNotFoundException;
import pzinsta.pizzeria.web.client.PizzaServiceClient;
import pzinsta.pizzeria.web.model.OrderDTO;
import pzinsta.pizzeria.web.model.OrderItemDTO;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;
    @Autowired
    private PizzaServiceClient pizzaServiceClient;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showUserProfileForm(Model model, Principal principal) {
        Customer customer = customerService.getCustomerByUsername(principal.getName())
                .orElseThrow(CustomerNotFoundException::new);
        model.addAttribute("orders", getOrders(customer.getOrders()));
        model.addAttribute("customer", customer);
        return "customerProfile";
    }

    private Collection<OrderDTO> getOrders(Collection<Order> orders) {
        return orders.stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            List<OrderItemDTO> orderItems = order.getOrderItems().stream().map(orderItem -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setPizza(pizzaServiceClient.findPizzaById(orderItem.getPizzaId()));
                orderItemDTO.setQuantity(orderItem.getQuantity());
                return orderItemDTO;
            }).collect(Collectors.toList());
            orderDTO.setOrderItems(orderItems);
            orderDTO.setTrackingNumber(order.getTrackingNumber());
            return orderDTO;
        }).collect(Collectors.toList());
    }

    @PostMapping
    // TODO: 4/3/2018 create a form dto class for customer information ?
    public String processUserProfileForm(@ModelAttribute @Valid Customer customer, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "customerProfile";
        }
        updateExistingCustomer(customer, principal);
        return "redirect:customer";
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCustomerNotFoundException() {

    }

    private void updateExistingCustomer(Customer customer, Principal principal) {
        Optional<Customer> customerOptional = customerService.getCustomerByUsername(principal.getName());
        customerOptional.ifPresent(existingCustomer -> {
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            customerService.updateCustomer(existingCustomer);
        });
    }
}
