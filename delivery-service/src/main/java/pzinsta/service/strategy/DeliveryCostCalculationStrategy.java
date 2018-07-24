package pzinsta.service.strategy;

import pzinsta.model.Delivery;

import javax.money.MonetaryAmount;

public interface DeliveryCostCalculationStrategy {

    MonetaryAmount calculateCost(Delivery delivery);

}
