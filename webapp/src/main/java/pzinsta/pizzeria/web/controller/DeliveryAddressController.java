package pzinsta.pizzeria.web.controller;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.model.user.Customer;
import pzinsta.pizzeria.model.user.DeliveryAddress;
import pzinsta.pizzeria.service.CustomerNotFoundException;
import pzinsta.pizzeria.service.CustomerService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/customer/deliveryAddress")
public class DeliveryAddressController {

    private CustomerService customerService;

    @Autowired
    public DeliveryAddressController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{deliveryAddressIndex}")
    public String showEditDeliveryAddressForm(@PathVariable("deliveryAddressIndex") int deliveryAddressIndex, Model model, Principal principal) {
        Customer customer = getCustomerFromPrincipal(principal);
        model.addAttribute("deliveryAddress", customer.getDeliveryAddresses().get(deliveryAddressIndex));
        return "editDeliveryAddress";
    }

    @PostMapping("/{deliveryAddressIndex}")
    public String updateDeliveryAddress(@ModelAttribute @Valid DeliveryAddress deliveryAddress, BindingResult bindingResult, @PathVariable("deliveryAddressIndex") int deliveryAddressIndex, @RequestParam(name = "returnUrl", defaultValue = "/customer") String returnUrl, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "editDeliveryAddress";
        }
        Customer customer = getCustomerFromPrincipal(principal);
        DeliveryAddress deliveryAddressToUpdate = customer.getDeliveryAddresses().get(deliveryAddressIndex);
        deliveryAddressToUpdate.setCity(deliveryAddress.getCity());
        deliveryAddressToUpdate.setStreet(deliveryAddress.getStreet());
        deliveryAddressToUpdate.setHouseNumber(deliveryAddress.getApartmentNumber());
        deliveryAddressToUpdate.setApartmentNumber(deliveryAddress.getApartmentNumber());
        customerService.updateCustomer(customer);
        return Joiner.on(StringUtils.EMPTY).join("redirect:", returnUrl);
    }

    @GetMapping("/{deliveryAddressIndex}/remove")
    public String removeDeliveryAddress(@PathVariable("deliveryAddressIndex") int deliveryAddressIndex, @RequestParam(name = "returnUrl", defaultValue = "/customer") String returnUrl, Principal principal) {
        Customer customer = getCustomerFromPrincipal(principal);
        customer.getDeliveryAddresses().remove(deliveryAddressIndex); // TODO: 4/9/2018 handle indexoutofbounds case
        customerService.updateCustomer(customer);
        return Joiner.on(StringUtils.EMPTY).join("redirect:", returnUrl);
    }

    @GetMapping("/add")
    public String showAddDeliveryAddressForm(Model model) {

        model.addAttribute("deliveryAddress", new DeliveryAddress());

        return "addDeliveryAddress";
    }

    @PostMapping("/add")
    public String addDeliveryAddress(@ModelAttribute @Valid DeliveryAddress deliveryAddress, BindingResult bindingResult, Principal principal, @RequestParam(name = "returnUrl", defaultValue = "/customer") String returnUrl) {
        if (bindingResult.hasErrors()) {
            return "addDeliveryAddress";
        }

        Customer customer = getCustomerFromPrincipal(principal);
        customer.getDeliveryAddresses().add(deliveryAddress);
        customerService.updateCustomer(customer);
        return Joiner.on(StringUtils.EMPTY).join("redirect:", returnUrl);
    }

    private Customer getCustomerFromPrincipal(Principal principal) {
        return customerService.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
    }
}
