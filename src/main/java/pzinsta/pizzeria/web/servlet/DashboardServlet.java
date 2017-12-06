package pzinsta.pizzeria.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderStatus;
import pzinsta.pizzeria.service.OrderService;

public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrderService orderService;

	@Override
	public void init() throws ServletException {
	    orderService = (OrderService) getServletContext().getAttribute("orderService");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String[] orderStatusParameterValues = request.getParameterValues("orderStatus");
		if(orderStatusParameterValues != null) {
			Set<OrderStatus> orderStatuses = Arrays.asList(orderStatusParameterValues).stream().distinct().map(OrderStatus::valueOf).collect(Collectors.toSet());
			Collection<Order> ordersByStatus = orderService.getOrdersByStatus(orderStatuses);
			request.setAttribute("orders", ordersByStatus);
		}
		else {
			request.setAttribute("orders", orderService.getUnfinishedOrders());
		}
		
		request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
