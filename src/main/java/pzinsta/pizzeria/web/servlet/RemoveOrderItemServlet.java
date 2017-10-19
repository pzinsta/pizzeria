package pzinsta.pizzeria.web.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pzinsta.pizzeria.model.order.Order;

public class RemoveOrderItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderItemId = request.getParameter("orderItemId");
		Optional<Order> orderOptional = Optional.ofNullable((Order)request.getSession().getAttribute("order"));
		orderOptional.ifPresent(order -> order.removeOrderItemById(orderItemId));
		response.sendRedirect(request.getContextPath());
	}

}
