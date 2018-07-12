package pzinsta.pizzeria.web.service;

import com.google.common.collect.ImmutableList;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderItem;
import pzinsta.pizzeria.web.client.PizzaServiceClient;
import pzinsta.pizzeria.web.client.dto.pizza.Crust;
import pzinsta.pizzeria.web.client.dto.pizza.Ingredient;
import pzinsta.pizzeria.web.client.dto.pizza.Pizza;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaItem;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaSide;
import pzinsta.pizzeria.web.client.dto.pizza.PizzaSize;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static org.mockito.Mockito.when;

public class OrderCostCalculationServiceImplTest {

    private final static Long PIZZA_ID = 42L;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private PizzaServiceClient pizzaServiceClient;

    @InjectMocks
    private OrderCostCalculationServiceImpl orderCostCalculationService;

    @Test
    public void shouldCalculateCost() throws Exception {
        // given
        Order order = createOrder();
        MonetaryAmount pizzaCost = fromDouble(33);
        when(pizzaServiceClient.calculatePizzaCost(PIZZA_ID)).thenReturn(pizzaCost);

        // when
        MonetaryAmount result = orderCostCalculationService.calculateCost(order);

        // then
        Assertions.assertThat(result).isEqualTo(fromDouble(99));
    }

    private static Order createOrder() {
        Order order = new Order();

        PizzaSize pizzaSize = new PizzaSize();
        pizzaSize.setPrice(fromDouble(15));
        pizzaSize.setIngredientCostFactor(3);

        Crust crust = new Crust();
        crust.setPrice(fromDouble(3));

        Ingredient tomato = new Ingredient();
        tomato.setPrice(fromDouble(1));

        Ingredient cheese = new Ingredient();
        cheese.setPrice(fromDouble(2));

        Ingredient mushroom = new Ingredient();
        mushroom.setPrice(fromDouble(1));

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
        pizza.setId(PIZZA_ID);
        pizza.setSize(pizzaSize);
        pizza.setCrust(crust);
        pizza.setLeftPizzaSide(leftPizzaSide);
        pizza.setRightPizzaSide(rightPizzaSide);

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(3);
        orderItem.setPizzaId(pizza.getId());
        order.setOrderItems(ImmutableList.of(orderItem));

        return order;
    }

    private static MonetaryAmount fromDouble(double amount) {
        return Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(amount).create();
    }
}