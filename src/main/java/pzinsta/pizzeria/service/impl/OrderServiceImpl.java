package pzinsta.pizzeria.service.impl;

import pzinsta.pizzeria.dao.OrderDao;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderItem;
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

}
