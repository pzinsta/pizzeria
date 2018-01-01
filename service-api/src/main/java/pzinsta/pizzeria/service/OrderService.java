package pzinsta.pizzeria.service;

import java.util.Collection;

import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderStatus;

public interface OrderService {
	void saveOrder(Order order);
	Collection<Order> getUnfinishedOrders();
	Collection<Order> getOrdersByStatus(Collection<OrderStatus> statuses);
	void updateOrderStatus(Long orderId, Long statusId);
}
