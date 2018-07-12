package pzinsta.pizzeria.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderItem;
import pzinsta.pizzeria.service.OrderCostCalculationService;
import pzinsta.pizzeria.web.client.PizzaServiceClient;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

@Service("orderCostCalculationService")
public class OrderCostCalculationServiceImpl implements OrderCostCalculationService {

    private static final MonetaryAmount ZERO = Monetary.getDefaultAmountFactory().setNumber(0).setCurrency("USD").create();

    private PizzaServiceClient pizzaServiceClient;

    @Autowired
    public OrderCostCalculationServiceImpl(PizzaServiceClient pizzaServiceClient) {
        this.pizzaServiceClient = pizzaServiceClient;
    }

    @Override
    public MonetaryAmount calculateCost(Order order) {
        return order.getOrderItems().stream()
                .map(this::calculateOrderItemCost)
                .reduce(ZERO, MonetaryAmount::add);
    }

    private MonetaryAmount calculateOrderItemCost(OrderItem orderItem) {
        return pizzaServiceClient.calculatePizzaCost(orderItem.getPizzaId()).multiply(orderItem.getQuantity());
    }

    public PizzaServiceClient getPizzaServiceClient() {
        return pizzaServiceClient;
    }

    public void setPizzaServiceClient(PizzaServiceClient pizzaServiceClient) {
        this.pizzaServiceClient = pizzaServiceClient;
    }
}
