package pzinsta.service.strategy.impl;

import pzinsta.model.Delivery;
import pzinsta.service.strategy.DeliveryCostCalculationStrategy;

import javax.money.MonetaryAmount;

public class FixedDeliveryCostCalculationStrategy implements DeliveryCostCalculationStrategy {

    private MonetaryAmount cost;

    public FixedDeliveryCostCalculationStrategy(MonetaryAmount cost) {
        this.cost = cost;
    }

    @Override
    public MonetaryAmount calculateCost(Delivery delivery) {
        return cost;
    }

    public MonetaryAmount getCost() {
        return cost;
    }

    public void setCost(MonetaryAmount cost) {
        this.cost = cost;
    }
}
