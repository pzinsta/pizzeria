package pzinsta.pizzeria.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;

import pzinsta.pizzeria.dao.impl.OrderDaoImpl;
import pzinsta.pizzeria.dao.impl.PizzaDaoImpl;
import pzinsta.pizzeria.dao.impl.UserDaoImpl;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderStatus;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.PizzaService;
import pzinsta.pizzeria.service.UserService;
import pzinsta.pizzeria.service.impl.OrderServiceImpl;
import pzinsta.pizzeria.service.impl.PizzaServiceImpl;
import pzinsta.pizzeria.service.impl.UserServiceImpl;

public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrderService orderService;

	public DashboardServlet() {
		UserService userService = new UserServiceImpl(new UserDaoImpl());
		PizzaService pizzaService = new PizzaServiceImpl(new PizzaDaoImpl());
		orderService = new OrderServiceImpl(new OrderDaoImpl(), userService, pizzaService);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<Object> staffUserOptional = Optional.ofNullable(request.getSession().getAttribute("staff-user"));
		String[] orderStatusParameterValues = request.getParameterValues("orderStatus");
		if(orderStatusParameterValues != null) {
			Set<OrderStatus> orderStatuses = Arrays.asList(orderStatusParameterValues).stream().distinct().map(OrderStatus::valueOf).collect(Collectors.toSet());
			Collection<Order> ordersByStatus = orderService.getOrdersByStatus(orderStatuses);
			request.setAttribute("orders", ordersByStatus);
		}
		else {
			request.setAttribute("orders", orderService.getUnfinishedOrders());
		}
		if (staffUserOptional.isPresent()) {
			request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
		}
		else { //TODO: move this to a filter
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
