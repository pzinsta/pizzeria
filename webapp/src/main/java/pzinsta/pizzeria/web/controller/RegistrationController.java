package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;
import pzinsta.pizzeria.service.CustomerRegistrationService;
import pzinsta.pizzeria.service.dto.CustomerRegistrationDTO;
import pzinsta.pizzeria.web.form.CustomerRegistrationForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/account/register")
public class RegistrationController {

    private CustomerRegistrationService customerRegistrationService;

    @Autowired
    public RegistrationController(CustomerRegistrationService customerRegistrationService) {
        this.customerRegistrationService = customerRegistrationService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("customerRegistrationForm", new CustomerRegistrationForm());
        return "register";
    }

    @PostMapping
    public View processRegistrationForm(@ModelAttribute @Valid CustomerRegistrationForm customerRegistrationForm, BindingResult bindingResult, HttpServletRequest httpServletRequest, @RequestParam(name = "returnUrl", defaultValue = "/") String returnUrl) throws ServletException {
        if (bindingResult.hasErrors()) {
            return new InternalResourceView("register");
        }

        customerRegistrationService.processRegistration(convertRegistrationFormToRegistrationDTO(customerRegistrationForm));

        httpServletRequest.login(customerRegistrationForm.getUsername(), customerRegistrationForm.getPassword());

        RedirectView redirectView = new RedirectView(returnUrl);
        redirectView.setContextRelative(true);
        return redirectView;
    }

    private CustomerRegistrationDTO convertRegistrationFormToRegistrationDTO(CustomerRegistrationForm customerRegistrationForm) {
        CustomerRegistrationDTO customerRegistrationDTO = new CustomerRegistrationDTO();
        customerRegistrationDTO.setUsername(customerRegistrationForm.getUsername());
        customerRegistrationDTO.setPassword(customerRegistrationForm.getPassword());
        customerRegistrationDTO.setFirstName(customerRegistrationForm.getFirstName());
        customerRegistrationDTO.setLastName(customerRegistrationForm.getLastName());
        customerRegistrationDTO.setEmail(customerRegistrationForm.getEmail());
        customerRegistrationDTO.setPhoneNumber(customerRegistrationForm.getPhoneNumber());
        return customerRegistrationDTO;
    }

}
