package pzinsta.pizzeria.web.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pzinsta.pizzeria.dao.impl.UserDaoImpl;
import pzinsta.pizzeria.model.User;
import pzinsta.pizzeria.service.UserService;
import pzinsta.pizzeria.service.impl.UserServiceImpl;

public class LogInHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger();
	
	private UserService userService = new UserServiceImpl(new UserDaoImpl());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String from = request.getParameter("from");
		Optional<User> userOptional = userService.getRegisteredUserByEmailAndPassword(email, password);
		if (userOptional.isPresent()) {
			request.getSession().setAttribute("customer", userOptional.get());
			response.sendRedirect(from);
		}
		else {
			request.setAttribute("error", "Email and/or password are invalid.");
			String fromWithoutContextPath = StringUtils.substring(from, request.getContextPath().length());
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(fromWithoutContextPath);
			requestDispatcher.forward(request, response);
		}
	}

}
