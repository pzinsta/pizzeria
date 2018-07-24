package pzinsta.service;

import com.google.common.collect.ImmutableList;
import org.javamoney.moneta.Money;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pzinsta.exception.PizzaNotFoundException;
import pzinsta.model.Crust;
import pzinsta.model.Ingredient;
import pzinsta.model.Pizza;
import pzinsta.model.PizzaItem;
import pzinsta.model.PizzaSide;
import pzinsta.model.PizzaSize;
import pzinsta.repository.PizzaRepository;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

public class PizzaServiceTest {
    private static final long PIZZA_ID = 42L;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private PizzaService pizzaService;

    @Mock
    private PizzaRepository pizzaRepository;

    @Test
    public void shouldFindAllPizzas() throws Exception {
        // given
        List<Pizza> pizzas = ImmutableList.of();
        when(pizzaRepository.findAll()).thenReturn(pizzas);

        // when
        Iterable<Pizza> result = pizzaService.findAll();

        // then
        assertThat(result).isSameAs(pizzas);
    }

    @Test
    public void shouldFindById() throws Exception {
        // given
        Pizza pizza = new Pizza();
        when(pizzaRepository.findById(PIZZA_ID)).thenReturn(Optional.of(pizza));

        // when
        Pizza result = pizzaService.findById(PIZZA_ID);

        // then
        assertThat(result).isSameAs(pizza);
    }

    @Test
    public void shouldThrowExceptionWhenCannotFindById() throws Exception {
        // given
        when(pizzaRepository.findById(PIZZA_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> pizzaService.findById(PIZZA_ID));

        // then
        assertThat(throwable).isInstanceOf(PizzaNotFoundException.class);
    }

    @Test
    public void shouldSavePizza() throws Exception {
        // given
        Pizza pizza = new Pizza();
        when(pizzaRepository.save(pizza)).thenReturn(pizza);

        // when
        Pizza result = pizzaService.save(pizza);

        // then
        assertThat(result).isSameAs(pizza);
    }

    @Test
    public void shouldCalculateCost() throws Exception {
        // given
        Pizza pizza = createPizza();

        // when
        MonetaryAmount result = pizzaService.calculateCost(pizza);

        // then
        assertThat(result).isEqualTo(Money.of(33, "USD"));
    }

    @Test
    public void shouldCalculateCostById() throws Exception {
        // given
        when(pizzaRepository.findById(PIZZA_ID)).thenReturn(Optional.ofNullable(createPizza()));

        // when
        MonetaryAmount result = pizzaService.calculateCostById(PIZZA_ID);

        // then
        assertThat(result).isEqualTo(Money.of(33, "USD"));
    }

    @Test
    public void shouldThrowExceptionWhenCalculatingCostByIdAndCannotFindPizza() throws Exception {
        // given
        when(pizzaRepository.findById(PIZZA_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> pizzaService.calculateCostById(PIZZA_ID));

        // then
        assertThat(throwable).isInstanceOf(PizzaNotFoundException.class);
    }

    private Pizza createPizza() {
        PizzaSize pizzaSize = new PizzaSize();
        pizzaSize.setPrice(Money.of(15, "USD"));
        pizzaSize.setIngredientCostFactor(3);

        Crust crust = new Crust();
        crust.setPrice(Money.of(3, "USD"));

        Ingredient tomato = new Ingredient();
        tomato.setPrice(Money.of(1, "USD"));

        Ingredient cheese = new Ingredient();
        cheese.setPrice(Money.of(2, "USD"));

        Ingredient mushroom = new Ingredient();
        mushroom.setPrice(Money.of(1, "USD"));

        PizzaItem tomatoPizzaItem = new PizzaItem();
        tomatoPizzaItem.setIngredient(tomato);
        tomatoPizzaItem.setQuantity(2);

        PizzaItem cheesePizzaItem = new PizzaItem();
        cheesePizzaItem.setIngredient(cheese);
        cheesePizzaItem.setQuantity(1);

        PizzaSide leftPizzaSide = new PizzaSide();
        leftPizzaSide.setPizzaItems(ImmutableList.of(tomatoPizzaItem, cheesePizzaItem));

        PizzaItem mushroomPizzaItem = new PizzaItem();
        mushroomPizzaItem.setIngredient(mushroom);
        mushroomPizzaItem.setQuantity(1);

        PizzaSide rightPizzaSide = new PizzaSide();
        rightPizzaSide.setPizzaItems(ImmutableList.of(mushroomPizzaItem));

        Pizza pizza = new Pizza();
        pizza.setSize(pizzaSize);
        pizza.setCrust(crust);
        pizza.setLeftPizzaSide(leftPizzaSide);
        pizza.setRightPizzaSide(rightPizzaSide);

        return pizza;
    }

}