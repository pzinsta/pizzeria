package pzinsta.pizzeria.service.impl;

import static pzinsta.pizzeria.model.order.OrderStatus.BEING_DELIVERED;
import static pzinsta.pizzeria.model.order.OrderStatus.NOT_PAID;
import static pzinsta.pizzeria.model.order.OrderStatus.PAID;
import static pzinsta.pizzeria.model.order.OrderStatus.READY;

import java.util.Collection;
import java.util.Optional;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import pzinsta.pizzeria.dao.OrderDao;
import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderItem;
import pzinsta.pizzeria.model.order.OrderStatus;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.PizzaService;
import pzinsta.pizzeria.service.UserService;

public class OrderServiceImpl implements OrderService {

	private OrderDao orderDao;
	private UserService userService;
	private PizzaService pizzaService;
	
	public OrderServiceImpl(OrderDao orderDao, UserService userService, PizzaService pizzaService) {
		this.orderDao = orderDao;
		this.userService = userService;
		this.pizzaService = pizzaService;
	}
	
	@Override
	public void saveOrder(Order order) {
		if(order.getCustomer().getId() == null) { //TODO: utility method
			userService.createCustomer(order.getCustomer());
		}
		else {
			userService.updateCustomer(order.getCustomer());
		}
		
		order.getOrderItems().stream().map(OrderItem::getPizza).forEach(pizzaService::savePizza);
		
		orderDao.saveOrder(order);
	}

	@Override
	public Collection<Order> getUnfinishedOrders() {
		return getOrdersByStatus(ImmutableSet.of(NOT_PAID, PAID, READY, BEING_DELIVERED));
	}

	private void populateOrderData(Order order) {
		Optional<Customer> customerById = userService.getCustomerById(order.getCustomer().getId());
		order.setCustomer(customerById.get());
		for (OrderItem orderItem : order.getOrderItems()) {
			orderItem.setPizza(pizzaService.getPizza(orderItem.getPizza().getId()));
		}
	}

	@Override
	public Collection<Order> getOrdersByStatus(Collection<OrderStatus> statuses) {
		Collection<Order> ordersByStatus = orderDao.getOrdersByStatus(Sets.immutableEnumSet(statuses));
		for (Order order : ordersByStatus) {
			populateOrderData(order);
		}
		return ordersByStatus;
	}

    @Override
    public void updateOrderStatus(Long orderId, Long statusId) {
        orderDao.updateOrderStatus(orderId, statusId);
    }

}
