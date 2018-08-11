package pzinsta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pzinsta.client.PizzaServiceClient;
import pzinsta.exception.OrderNotFoundException;
import pzinsta.model.Order;
import pzinsta.model.OrderEvent;
import pzinsta.model.OrderEventType;
import pzinsta.model.OrderItem;
import pzinsta.repository.OrderRepository;
import pzinsta.strategy.TrackingNumberGenerationStrategy;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.time.Instant;

@Service
public class OrderService {

    private static final MonetaryAmount ZERO = Monetary.getDefaultAmountFactory().setNumber(0).setCurrency("USD").create();

    private OrderRepository orderRepository;
    private TrackingNumberGenerationStrategy trackingNumberGenerationStrategy;
    private PizzaServiceClient pizzaServiceClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, TrackingNumberGenerationStrategy trackingNumberGenerationStrategy, PizzaServiceClient pizzaServiceClient) {
        this.orderRepository = orderRepository;
        this.trackingNumberGenerationStrategy = trackingNumberGenerationStrategy;
        this.pizzaServiceClient = pizzaServiceClient;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order findByTrackingNumber(String trackingNumber) {
        return orderRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> OrderNotFoundException.withTrackingNumber(trackingNumber));
    }

    public Iterable<Order> findAllByCustomerId(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    public Order post(Order order) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOccurredOn(Instant.now());
        orderEvent.setOrderEventType(OrderEventType.CREATED);
        order.getOrderEvents().add(orderEvent);

        order.setTrackingNumber(trackingNumberGenerationStrategy.generateTrackingNumber(order));

        return orderRepository.save(order);
    }

    public MonetaryAmount calculateCost(Order order) {
        return order.getOrderItems().stream()
                .map(this::calculateOrderItemCost)
                .reduce(ZERO, MonetaryAmount::add);
    }

    public MonetaryAmount calculateCostById(Long id) {
        return calculateCost(findById(id));
    }

    private MonetaryAmount calculateOrderItemCost(OrderItem orderItem) {
        return pizzaServiceClient.calculatePizzaCost(orderItem.getPizzaId()).multiply(orderItem.getQuantity());
    }
}
