package pzinsta.pizzeria.web.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.service.UserService;

public class LogInHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService;

	@Override
	public void init() throws ServletException {
	    userService = (UserService) getServletContext().getAttribute("userService");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String from = request.getParameter("from");
		Optional<Customer> customerOptional = userService.getRegisteredCustomerByEmailAndPassword(email, password);
		if (customerOptional.isPresent()) {
			request.getSession().setAttribute("customer", customerOptional.get());
			response.sendRedirect(from);
		} else {
			request.setAttribute("error", "Email and/or password are invalid.");
			String fromWithoutContextPath = StringUtils.substring(from, request.getContextPath().length());
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(fromWithoutContextPath);
			requestDispatcher.forward(request, response);
		}
	}

}
