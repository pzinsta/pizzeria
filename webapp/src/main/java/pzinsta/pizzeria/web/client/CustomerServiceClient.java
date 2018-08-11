package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.web.client.dto.user.Customer;
import pzinsta.pizzeria.web.client.dto.user.CustomerRegistration;
import pzinsta.pizzeria.web.client.dto.user.DeliveryAddress;

import java.util.Optional;

@Component
@FeignClient(name = "user-service",
        path = "/customers",
        qualifier = "customerServiceClient",
        decode404 = true
)
public interface CustomerServiceClient {

    @GetMapping("/{id}")
    Optional<Customer> findById(@PathVariable("id") Long id);

    @GetMapping(params = "username")
    Optional<Customer> findByUsername(@RequestParam("username") String username);

    @PostMapping("/registration")
    Customer register(@RequestBody CustomerRegistration customerRegistration);

    @PostMapping
    Customer create(@RequestBody Customer customer);

    @PutMapping("/{id}")
    Customer update(@PathVariable("id") Long id, @RequestBody Customer customer);

    @GetMapping("/{customerId}/deliveryAddresses/{deliveryAddressId}")
    Optional<DeliveryAddress> findDeliveryAddressById(@PathVariable("customerId") Long customerId, @PathVariable("deliveryAddressId") Long deliveryAddressId);

    @PostMapping("/{id}/deliveryAddresses")
    DeliveryAddress addDeliveryAddress(@PathVariable("id") Long customerId, @RequestBody DeliveryAddress deliveryAddress);

    @PutMapping("/{customerId}/deliveryAddresses/{deliveryAddressId}")
    DeliveryAddress updateDeliveryAddress(@PathVariable("customerId") Long customerId, @PathVariable("deliveryAddressId") Long deliveryAddressId, @RequestBody DeliveryAddress deliveryAddress);

    @DeleteMapping("/{customerId}/deliveryAddresses/{deliveryAddressId}")
    void deleteDeliveryAddress(@PathVariable("customerId") Long customerId, @PathVariable("deliveryAddressId") Long deliveryAddressId);
}
