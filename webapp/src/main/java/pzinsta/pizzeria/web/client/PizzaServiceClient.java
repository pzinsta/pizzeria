package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pzinsta.pizzeria.web.client.dto.pizza.BakeStyle;
import pzinsta.pizzeria.web.client.dto.pizza.Crust;
import pzinsta.pizzeria.web.client.dto.pizza.CutStyle;
import pzinsta.pizzeria.web.client.dto.pizza.Ingredient;
import pzinsta.pizzeria.web.client.dto.pizza.Pizza;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaSize;

import javax.money.MonetaryAmount;
import java.util.Collection;

@Component
@FeignClient(name = "pizzas", url = "${pizza.service.url}")
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
    Ingredient findIngredientById(@PathVariable("id") Long id);

    @GetMapping("/pizzas/{id}")
    Pizza findPizzaById(@PathVariable("id") Long id);

    @GetMapping("/pizzaSizes/{id}")
    PizzaSize findPizzaSizeById(@PathVariable("id") Long id);

    @GetMapping("/cutStyles/{id}")
    CutStyle findCutStyleById(@PathVariable("id") Long id);

    @GetMapping("/crusts/{id}")
    Crust findCrustById(@PathVariable("id") Long id);

    @GetMapping("/bakeStyles/{id}")
    BakeStyle findBakeStyleById(@PathVariable("id") Long id);

    @PostMapping("/pizzas")
    Pizza savePizza(Pizza pizza);

    @GetMapping("/pizzas/{id}/cost")
    MonetaryAmount calculatePizzaCost(@PathVariable("id") Long id);
}
