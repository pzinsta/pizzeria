package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.service.CustomerRegistrationService;
import pzinsta.pizzeria.service.dto.CustomerRegistrationDTO;
import pzinsta.pizzeria.web.form.CustomerRegistrationForm;
import pzinsta.pizzeria.web.validator.CustomerRegistrationFormValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/account/register")
public class RegistrationController {

    private CustomerRegistrationService customerRegistrationService;
    private CustomerRegistrationFormValidator customerRegistrationFormValidator;

    @Autowired
    public RegistrationController(CustomerRegistrationService customerRegistrationService, CustomerRegistrationFormValidator customerRegistrationFormValidator) {
        this.customerRegistrationService = customerRegistrationService;
        this.customerRegistrationFormValidator = customerRegistrationFormValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(customerRegistrationFormValidator);
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("customerRegistrationForm", new CustomerRegistrationForm());
        return "register";
    }

    @PostMapping
    public String processRegistrationForm(@Valid @ModelAttribute("customerRegistrationForm") CustomerRegistrationForm customerRegistrationForm, BindingResult bindingResult, HttpServletRequest httpServletRequest, @RequestParam(name = "returnUrl", defaultValue = "/") String returnUrl) throws ServletException {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        customerRegistrationService.processRegistration(convertRegistrationFormToRegistrationDTO(customerRegistrationForm));

        httpServletRequest.login(customerRegistrationForm.getUsername(), customerRegistrationForm.getPassword());

        return "redirect:" + returnUrl;
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
