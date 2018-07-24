package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.dto.CustomerRegistration;
import pzinsta.exception.CustomerNotFoundException;
import pzinsta.model.Customer;
import pzinsta.model.DeliveryAddress;
import pzinsta.model.User;
import pzinsta.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Long id) {
        return customerService.findById(id);
    }


    @GetMapping
    public User findByUsername(@RequestParam("username") String username) {
        return customerService.findByUsername(username);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer register(@RequestBody CustomerRegistration customerRegistration) {
        return customerService.register(customerRegistration);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return customerService.update(id, customer);
    }

    @PostMapping("/{id}/deliveryAddresses")
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryAddress addDeliveryAddress(@PathVariable("id") Long id, @RequestBody DeliveryAddress deliveryAddress) {
        return customerService.addDeliveryAddress(id, deliveryAddress);
    }

    @GetMapping("/{customerId}/deliveryAddresses/{deliveryAddressId}")
    public DeliveryAddress findDeliveryAddressById(@PathVariable("customerId") Long customerId, @PathVariable("deliveryAddressId") Long deliveryAddressId) {
        return customerService.findDeliveryAddressById(customerId, deliveryAddressId);
    }

    @PutMapping("/{customerId}/deliveryAddresses/{deliveryAddressId}")
    public DeliveryAddress updateDeliveryAddress(@PathVariable("customerId") Long customerId, @PathVariable("deliveryAddressId") Long deliveryAddressId, @RequestBody DeliveryAddress deliveryAddress) {
        return customerService.updateDeliveryAddress(customerId, deliveryAddressId, deliveryAddress);
    }

    @DeleteMapping("/{customerId}/deliveryAddresses/{deliveryAddressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeliveryAddress(@PathVariable("customerId") Long customerId, @PathVariable("deliveryAddressId") Long deliveryAddressId) {
        customerService.deleteDeliveryAddress(customerId, deliveryAddressId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(CustomerNotFoundException e) {
        return e.getMessage();
    }
}
