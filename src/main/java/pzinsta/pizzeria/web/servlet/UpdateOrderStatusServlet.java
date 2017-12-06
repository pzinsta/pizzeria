package pzinsta.pizzeria.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pzinsta.pizzeria.service.OrderService;

public class UpdateOrderStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrderService orderService;
	
	@Override
	public void init() throws ServletException {
	    orderService = (OrderService) getServletContext().getAttribute("orderService");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Long orderId = Long.valueOf(request.getParameter("orderId"));
	    Long statusId = Long.valueOf(request.getParameter("statusId"));
	    String from = request.getParameter("from");
	    
	    orderService.updateOrderStatus(orderId, statusId);
	    
	    response.sendRedirect(from);
	}

}
