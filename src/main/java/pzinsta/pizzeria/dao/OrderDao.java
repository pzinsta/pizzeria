package pzinsta.pizzeria.dao;

import pzinsta.pizzeria.model.order.Order;

public interface OrderDao {
	void saveOrder(Order order);
}
