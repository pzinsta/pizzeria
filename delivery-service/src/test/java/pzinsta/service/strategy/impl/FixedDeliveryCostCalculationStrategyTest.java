package pzinsta.service.strategy.impl;

import org.junit.Test;
import pzinsta.model.Delivery;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static org.assertj.core.api.Assertions.assertThat;

public class FixedDeliveryCostCalculationStrategyTest {

    @Test
    public void shouldCalculateCost() throws Exception {
        // given
        MonetaryAmount cost = Monetary.getDefaultAmountFactory()
                .setNumber(12.58)
                .setCurrency("USD")
                .create();
        Delivery delivery = new Delivery();
        FixedDeliveryCostCalculationStrategy fixedDeliveryCostCalculationStrategy = new FixedDeliveryCostCalculationStrategy(cost);

        // when
        MonetaryAmount result = fixedDeliveryCostCalculationStrategy.calculateCost(delivery);

        // then
        assertThat(result).isEqualTo(cost);
    }
}