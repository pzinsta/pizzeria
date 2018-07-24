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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pzinsta.pizzeria.web.client.CustomerServiceClient;
import pzinsta.pizzeria.web.client.dto.user.Customer;
import pzinsta.pizzeria.web.client.dto.user.DeliveryAddress;
import pzinsta.pizzeria.web.exception.CustomerNotFoundException;
import pzinsta.pizzeria.web.exception.DeliveryAddressNotFoundException;
import pzinsta.pizzeria.web.form.DeliveryAddressForm;
import pzinsta.pizzeria.web.validator.DeliveryAddressFormValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/customer/deliveryAddress")
public class DeliveryAddressController {

    private CustomerServiceClient customerServiceClient;
    private DeliveryAddressFormValidator deliveryAddressFormValidator;

    @Value("${delivery.cities}")
    private List<String> cities;

    @Autowired
    public DeliveryAddressController(CustomerServiceClient customerServiceClient, DeliveryAddressFormValidator deliveryAddressFormValidator) {
        this.customerServiceClient = customerServiceClient;
        this.deliveryAddressFormValidator = deliveryAddressFormValidator;
    }

    @ModelAttribute("cities")
    public List<String> cities() {
        return cities;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(deliveryAddressFormValidator);
    }

    @GetMapping("/{deliveryAddressId}")
    public String showEditDeliveryAddressForm(@PathVariable("deliveryAddressId") Long deliveryAddressId, Model model, Principal principal) {
        DeliveryAddress deliveryAddress = getDeliveryAddress(principal, deliveryAddressId);
        model.addAttribute("deliveryAddressForm", transformDeliveryAddressToDeliveryAddressForm(deliveryAddress));
        return "editDeliveryAddress";
    }

    @PostMapping("/{deliveryAddressId}")
    public String updateDeliveryAddress(@ModelAttribute @Valid DeliveryAddressForm deliveryAddressForm, BindingResult bindingResult, @PathVariable("deliveryAddressId") Long deliveryAddressId, @RequestParam(name = "returnUrl", defaultValue = "/customer") String returnUrl, Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "editDeliveryAddress";
        }
        Customer customer = getCustomerFromPrincipal(principal);
        DeliveryAddress deliveryAddress = transformDeliveryAddressFormToDeliveryAddress(deliveryAddressForm);
        customerServiceClient.updateDeliveryAddress(customer.getId(), deliveryAddressId, deliveryAddress);
        return "redirect:" + returnUrl;
    }

    @GetMapping("/{deliveryAddressId}/remove")
    public String removeDeliveryAddress(@PathVariable("deliveryAddressId") Long deliveryAddressId, @RequestParam(name = "returnUrl", defaultValue = "/customer") String returnUrl, Principal principal, RedirectAttributes redirectAttributes) {
        Customer customer = getCustomerFromPrincipal(principal);
        customerServiceClient.deleteDeliveryAddress(customer.getId(), deliveryAddressId);
        return "redirect:" + returnUrl;
    }

    @GetMapping("/add")
    public String showAddDeliveryAddressForm(Model model) {

        model.addAttribute("deliveryAddressForm", new DeliveryAddressForm());

        return "addDeliveryAddress";
    }

    @PostMapping("/add")
    public String addDeliveryAddress(@ModelAttribute @Valid DeliveryAddressForm deliveryAddressForm, BindingResult bindingResult, Principal principal, @RequestParam(name = "returnUrl", defaultValue = "/customer") String returnUrl, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "addDeliveryAddress";
        }

        Customer customer = getCustomerFromPrincipal(principal);
        DeliveryAddress deliveryAddress = transformDeliveryAddressFormToDeliveryAddress(deliveryAddressForm);
        customerServiceClient.addDeliveryAddress(customer.getId(), deliveryAddress);
        return "redirect:" + returnUrl;
    }

    private DeliveryAddress getDeliveryAddress(Principal principal, Long deliveryAddressId) {
        Customer customer = getCustomerFromPrincipal(principal);
        return customerServiceClient.findDeliveryAddressById(customer.getId(), deliveryAddressId)
                .orElseThrow(() -> new DeliveryAddressNotFoundException(deliveryAddressId));
    }

    private Customer getCustomerFromPrincipal(Principal principal) {
        return customerServiceClient.findByUsername(principal.getName())
                .orElseThrow(() -> CustomerNotFoundException.withUsername(principal.getName()));
    }

    private DeliveryAddressForm transformDeliveryAddressToDeliveryAddressForm(DeliveryAddress deliveryAddress) {
        DeliveryAddressForm deliveryAddressForm = new DeliveryAddressForm();
        deliveryAddressForm.setCity(deliveryAddress.getCity());
        deliveryAddressForm.setStreet(deliveryAddress.getStreet());
        deliveryAddressForm.setHouseNumber(deliveryAddress.getHouseNumber());
        deliveryAddressForm.setApartmentNumber(deliveryAddress.getApartmentNumber());
        return deliveryAddressForm;
    }

    private DeliveryAddress transformDeliveryAddressFormToDeliveryAddress(DeliveryAddressForm deliveryAddressForm) {
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setCity(deliveryAddressForm.getCity());
        deliveryAddress.setStreet(deliveryAddressForm.getStreet());
        deliveryAddress.setHouseNumber(deliveryAddressForm.getHouseNumber());
        deliveryAddress.setApartmentNumber(deliveryAddressForm.getApartmentNumber());
        return deliveryAddress;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public DeliveryAddressFormValidator getDeliveryAddressFormValidator() {
        return deliveryAddressFormValidator;
    }

    public void setDeliveryAddressFormValidator(DeliveryAddressFormValidator deliveryAddressFormValidator) {
        this.deliveryAddressFormValidator = deliveryAddressFormValidator;
    }
}
