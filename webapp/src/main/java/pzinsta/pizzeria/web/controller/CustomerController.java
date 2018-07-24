package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.web.client.CustomerServiceClient;
import pzinsta.pizzeria.web.client.OrderServiceClient;
import pzinsta.pizzeria.web.client.PizzaServiceClient;
import pzinsta.pizzeria.web.client.dto.order.Order;
import pzinsta.pizzeria.web.client.dto.order.OrderItem;
import pzinsta.pizzeria.web.client.dto.pizza.Pizza;
import pzinsta.pizzeria.web.client.dto.user.Customer;
import pzinsta.pizzeria.web.exception.CustomerNotFoundException;
import pzinsta.pizzeria.web.exception.PizzaNotFoundException;
import pzinsta.pizzeria.web.model.OrderDTO;
import pzinsta.pizzeria.web.model.OrderItemDTO;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private CustomerServiceClient customerServiceClient;
    private PizzaServiceClient pizzaServiceClient;
    private OrderServiceClient orderServiceClient;

    @Autowired
    public CustomerController(CustomerServiceClient customerServiceClient, PizzaServiceClient pizzaServiceClient, OrderServiceClient orderServiceClient) {
        this.customerServiceClient = customerServiceClient;
        this.pizzaServiceClient = pizzaServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    @GetMapping
    public String showUserProfileForm(Model model, Principal principal) {
        model.addAttribute("orders", getOrders(orderServiceClient.findAllByCustomerId(getCustomer(principal).getId())));
        model.addAttribute("customer", getCustomer(principal));
        return "customerProfile";
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

    private Customer getCustomer(Principal principal) {
        return customerServiceClient.findByUsername(principal.getName())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with username %s not found.", principal.getName())));
    }

    private Collection<OrderDTO> getOrders(Collection<Order> orders) {
        return orders.stream()
                .map(this::transformOrderToOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO transformOrderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        List<OrderItemDTO> orderItems = order.getOrderItems().stream()
                .map(this::transformOrderItemToOrderItemDTO)
                .collect(Collectors.toList());
        orderDTO.setOrderItems(orderItems);
        orderDTO.setTrackingNumber(order.getTrackingNumber());
        return orderDTO;
    }

    private OrderItemDTO transformOrderItemToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        Pizza pizza = pizzaServiceClient.findPizzaById(orderItem.getPizzaId())
                .orElseThrow(() -> new PizzaNotFoundException(orderItem.getPizzaId()));
        orderItemDTO.setPizza(pizza);
        orderItemDTO.setQuantity(orderItem.getQuantity());
        return orderItemDTO;
    }

    private void updateExistingCustomer(Customer customer, Principal principal) {
        Customer existingCustomer = getCustomer(principal);
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        customerServiceClient.update(existingCustomer.getId(), existingCustomer);
    }

    public CustomerServiceClient getCustomerServiceClient() {
        return customerServiceClient;
    }

    public void setCustomerServiceClient(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    public PizzaServiceClient getPizzaServiceClient() {
        return pizzaServiceClient;
    }

    public void setPizzaServiceClient(PizzaServiceClient pizzaServiceClient) {
        this.pizzaServiceClient = pizzaServiceClient;
    }

    public OrderServiceClient getOrderServiceClient() {
        return orderServiceClient;
    }

    public void setOrderServiceClient(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }
}
