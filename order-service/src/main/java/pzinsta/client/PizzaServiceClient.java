package pzinsta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.money.MonetaryAmount;

@Component
@FeignClient("pizza-service")
public interface PizzaServiceClient {

    @GetMapping("/pizzas/{id}/cost")
    MonetaryAmount calculatePizzaCost(@PathVariable("id") Long id);
}
