package pzinsta.pizzeria.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.util.UriComponentsBuilder;
import pzinsta.pizzeria.web.client.AccountServiceClient;
import pzinsta.pizzeria.web.client.CustomerServiceClient;
import pzinsta.pizzeria.web.client.dto.account.Account;
import pzinsta.pizzeria.web.client.dto.account.Role;
import pzinsta.pizzeria.web.client.dto.user.CustomerRegistration;
import pzinsta.pizzeria.web.form.CustomerRegistrationForm;
import pzinsta.pizzeria.web.service.GoogleReCaptchaService;
import pzinsta.pizzeria.web.validator.CustomerRegistrationFormValidator;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/account/register")
public class RegistrationController {

    private CustomerServiceClient customerServiceClient;
    private AccountServiceClient accountServiceClient;
    private CustomerRegistrationFormValidator customerRegistrationFormValidator;
    private GoogleReCaptchaService googleReCaptchaService;

    @Value("${recaptcha.public.key}")
    private String recaptchaPublicKey;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(customerRegistrationFormValidator);
    }

    @ModelAttribute("recaptchaPublicKey")
    public String recaptchaPublicKey() {
        return recaptchaPublicKey;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("customerRegistrationForm", new CustomerRegistrationForm());
        return "register";
    }

    @PostMapping
    public String processRegistrationForm(@Valid @ModelAttribute("customerRegistrationForm") CustomerRegistrationForm customerRegistrationForm,
                                          BindingResult bindingResult,
                                          @RequestParam(name = "returnUrl", defaultValue = "/") String returnUrl,
                                          @RequestParam("g-recaptcha-response") String recaptchaResponse) throws ServletException {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!googleReCaptchaService.isValid(recaptchaResponse)) {
            return "register";
        }

        accountServiceClient.create(convertRegistrationFormToAccount(customerRegistrationForm));
        customerServiceClient.register(convertRegistrationFormToRegistrationDTO(customerRegistrationForm));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/login").queryParam("returnUrl", returnUrl);
        return "redirect:" + uriComponentsBuilder.toUriString();
    }

    private Account convertRegistrationFormToAccount(CustomerRegistrationForm customerRegistrationForm) {
        Account account = new Account();
        account.setUsername(customerRegistrationForm.getUsername());
        account.setPassword(customerRegistrationForm.getPassword());
        account.setRoles(Collections.singleton(Role.REGISTERED_CUSTOMER));
        return account;
    }

    private CustomerRegistration convertRegistrationFormToRegistrationDTO(CustomerRegistrationForm customerRegistrationForm) {
        CustomerRegistration customerRegistration = new CustomerRegistration();
        customerRegistration.setUsername(customerRegistrationForm.getUsername());
        customerRegistration.setPassword(customerRegistrationForm.getPassword());
        customerRegistration.setFirstName(customerRegistrationForm.getFirstName());
        customerRegistration.setLastName(customerRegistrationForm.getLastName());
        customerRegistration.setEmail(customerRegistrationForm.getEmail());
        customerRegistration.setPhoneNumber(customerRegistrationForm.getPhoneNumber());
        return customerRegistration;
    }

    public GoogleReCaptchaService getGoogleReCaptchaService() {
        return googleReCaptchaService;
    }

    @Autowired
    public void setGoogleReCaptchaService(GoogleReCaptchaService googleReCaptchaService) {
        this.googleReCaptchaService = googleReCaptchaService;
    }

    @Autowired
    public void setCustomerRegistrationFormValidator(CustomerRegistrationFormValidator customerRegistrationFormValidator) {
        this.customerRegistrationFormValidator = customerRegistrationFormValidator;
    }

    public CustomerServiceClient getCustomerServiceClient() {
        return customerServiceClient;
    }

    @Autowired
    public void setCustomerServiceClient(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    public AccountServiceClient getAccountServiceClient() {
        return accountServiceClient;
    }

    @Autowired
    public void setAccountServiceClient(AccountServiceClient accountServiceClient) {
        this.accountServiceClient = accountServiceClient;
    }

    public CustomerRegistrationFormValidator getCustomerRegistrationFormValidator() {
        return customerRegistrationFormValidator;
    }

    public String getRecaptchaPublicKey() {
        return recaptchaPublicKey;
    }

    public void setRecaptchaPublicKey(String recaptchaPublicKey) {
        this.recaptchaPublicKey = recaptchaPublicKey;
    }
}
