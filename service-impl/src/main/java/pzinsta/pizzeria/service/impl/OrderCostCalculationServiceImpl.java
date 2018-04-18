package pzinsta.pizzeria.service.impl;

import org.springframework.stereotype.Service;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.service.OrderCostCalculationService;

import javax.money.MonetaryAmount;

@Service("orderCostCalculationService")
public class OrderCostCalculationServiceImpl implements OrderCostCalculationService {

    @Override
    public MonetaryAmount calculateCost(Order order) {
        return order.getCost();
    }
}
