package pzinsta.pizzeria.model;

import java.util.Set;

import pzinsta.pizzeria.model.order.Order;

public class Manager extends User {
	private Set<Order> processedOrders;

	public Set<Order> getProcessedOrders() {
		return processedOrders;
	}

	public void setProcessedOrders(Set<Order> processedOrders) {
		this.processedOrders = processedOrders;
	}
}
