package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pzinsta.pizzeria.web.client.dto.pizza.BakeStyle;
import pzinsta.pizzeria.web.client.dto.pizza.Crust;
import pzinsta.pizzeria.web.client.dto.pizza.CutStyle;
import pzinsta.pizzeria.web.client.dto.pizza.Ingredient;
import pzinsta.pizzeria.web.client.dto.pizza.Pizza;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaSize;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaTemplate;

import javax.money.MonetaryAmount;
import java.util.Collection;
import java.util.Optional;

@Component
@FeignClient(
        name = "pizza-service",
        url = "${pizza.service.url}",
        decode404 = true
)
public interface PizzaServiceClient {
    @GetMapping("/crusts")
    Collection<Crust> findAllCrusts();

    @GetMapping("/bakeStyles")
    Collection<BakeStyle> findAllBakeStyles();

    @GetMapping("/cutStyles")
    Collection<CutStyle> findAllCutStyles();

    @GetMapping("/pizzaSizes")
    Collection<PizzaSize> findAllPizzaSizes();

    @GetMapping("/ingredients")
    Collection<Ingredient> findAllIngredients();

    @GetMapping("/ingredients/{id}")
    Optional<Ingredient> findIngredientById(@PathVariable("id") Long id);

    @GetMapping("/pizzas/{id}")
    Optional<Pizza> findPizzaById(@PathVariable("id") Long id);

    @GetMapping("/pizzaSizes/{id}")
    Optional<PizzaSize> findPizzaSizeById(@PathVariable("id") Long id);

    @GetMapping("/cutStyles/{id}")
    Optional<CutStyle> findCutStyleById(@PathVariable("id") Long id);

    @GetMapping("/crusts/{id}")
    Optional<Crust> findCrustById(@PathVariable("id") Long id);

    @GetMapping("/bakeStyles/{id}")
    Optional<BakeStyle> findBakeStyleById(@PathVariable("id") Long id);

    @PostMapping("/pizzas")
    Pizza savePizza(@RequestBody Pizza pizza);

    @GetMapping("/pizzas/{id}/cost")
    Optional<MonetaryAmount> calculatePizzaCost(@PathVariable("id") Long id);

    @PostMapping("/pizzas/cost")
    MonetaryAmount calculatePizzaCost(@RequestBody Pizza pizza);

    @GetMapping("/pizzaTemplates")
    Collection<PizzaTemplate> findAllPizzaTemplates();

    @GetMapping("/pizzaTemplates/{id}")
    Optional<PizzaTemplate> findPizzaTemplateById(@PathVariable("id") Long id);
}
