package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.model.user.Customer;
import pzinsta.pizzeria.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private UserService userService;

    @Autowired
    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserProfileForm(Model model, Principal principal) {
        // TODO: 4/2/2018 handle a situation when no customer's found
        model.addAttribute("customer", userService.getCustomerByUsername(principal.getName()).get());
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

    private void updateExistingCustomer(Customer customer, Principal principal) {
        Optional<Customer> customerOptional = userService.getCustomerByUsername(principal.getName());
        customerOptional.ifPresent(existingCustomer -> {
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            userService.updateCustomer(existingCustomer);
        });
    }
}
