package pzinsta.pizzeria.dao;

import java.util.Collection;
import java.util.Set;

import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderStatus;

public interface OrderDao {
	void saveOrder(Order order);

	Collection<Order> getOrdersByStatus(Set<OrderStatus> orderStatuses);

    void updateOrderStatus(Long orderId, Long statusId);
}
