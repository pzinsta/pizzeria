package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.web.client.dto.order.Order;

import javax.money.MonetaryAmount;
import java.util.Collection;
import java.util.Optional;

@Component
@FeignClient(
        name = "order-service",
        path = "/orders",
        decode404 = true
)
public interface OrderServiceClient {

    @GetMapping
    Collection<Order> findAllByCustomerId(@RequestParam("customerId") Long customerId);

    @GetMapping
    Optional<Order> findByTrackingNumber(@RequestParam("trackingNumber") String trackingNumber);

    @PostMapping("/cost")
    MonetaryAmount calculateCost(@RequestBody Order order);

    @GetMapping("/{id}/cost")
    Optional<MonetaryAmount> calculateCostById(@PathVariable("id") Long id);

    @PostMapping
    Order post(@RequestBody Order order);
}
