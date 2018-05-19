package pzinsta.pizzeria.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pzinsta.pizzeria.model.order.Order;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static org.mockito.Mockito.when;

public class OrderCostCalculationServiceImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Order order;

    @Test
    public void shouldCalculateCost() throws Exception {
        // given
        OrderCostCalculationServiceImpl orderCostCalculationService = new OrderCostCalculationServiceImpl();
        MonetaryAmount cost = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(42.99).create();
        when(order.getCost()).thenReturn(cost);

        // when
        MonetaryAmount result = orderCostCalculationService.calculateCost(order);

        // then
        Assertions.assertThat(result).isEqualTo(cost);
    }
}