package pzinsta.pizzeria.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pzinsta.pizzeria.dao.impl.OrderDaoImpl;
import pzinsta.pizzeria.dao.impl.PizzaDaoImpl;
import pzinsta.pizzeria.dao.impl.UserDaoImpl;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.PizzaService;
import pzinsta.pizzeria.service.UserService;
import pzinsta.pizzeria.service.impl.OrderServiceImpl;
import pzinsta.pizzeria.service.impl.PizzaServiceImpl;
import pzinsta.pizzeria.service.impl.UserServiceImpl;

public class UpdateOrderStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrderService orderService;
	
	public UpdateOrderStatusServlet() {
	    UserService userService = new UserServiceImpl(new UserDaoImpl());
	    PizzaService pizzaService = new PizzaServiceImpl(new PizzaDaoImpl());
	    orderService = new OrderServiceImpl(new OrderDaoImpl(), userService, pizzaService);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Long orderId = Long.valueOf(request.getParameter("orderId"));
	    Long statusId = Long.valueOf(request.getParameter("statusId"));
	    String from = request.getParameter("from");
	    
	    orderService.updateOrderStatus(orderId, statusId);
	    
	    response.sendRedirect(from);
	}

}
