package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.web.client.dto.delivery.Delivery;

import javax.money.MonetaryAmount;
import java.util.Optional;

@Component
@FeignClient(
        name = "delivery-service",
        url = "${delivery.service.url}",
        path = "/deliveries",
        qualifier = "deliveryServiceClient",
        decode404 = true
)
public interface DeliveryServiceClient {

    @GetMapping("/{id}")
    Optional<Delivery> findById(@PathVariable("id") Long id);

    @GetMapping(params = "orderId")
    Optional<Delivery> findByOrderId(@RequestParam("orderId") Long orderId);

    @PostMapping
    Delivery create(@RequestBody Delivery delivery);

    @PostMapping("/cost")
    MonetaryAmount calculateCost(@RequestBody Delivery delivery);
}
