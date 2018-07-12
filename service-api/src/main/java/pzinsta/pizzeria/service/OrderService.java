package pzinsta.pizzeria.service;

import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.service.dto.PizzaOrderDTO;

import java.util.Collection;
import java.util.Optional;

public interface OrderService {

    Collection<Integer> getQuantities();

    void addOrderItemToCart(PizzaOrderDTO pizzaOrderDTO);

    void removeOrderItem(int orderItemIndex);

    void emptyCart();

    void replaceOrderItem(int orderItemIndex, PizzaOrderDTO pizzaOrderDTO);

    PizzaOrderDTO getPizzaOrderDtoByPizzaTemplateId(int orderItemIndex);

    Order postOrder(Order order);

    Order getOrderByTrackingNumber(String trackingNumber);

    void addReviewToOrderByTrackingNumber(String trackingNumber, Long reviewId);

    Optional<PizzaOrderDTO> getPizzaOrderDtoByPizzaTemplateId(Long orderItemId);
}
