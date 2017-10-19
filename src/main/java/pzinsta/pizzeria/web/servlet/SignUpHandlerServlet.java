package pzinsta.pizzeria.web.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableSet;

import pzinsta.pizzeria.dao.impl.UserDaoImpl;
import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.User;
import pzinsta.pizzeria.service.SignUpValidationService;
import pzinsta.pizzeria.service.UserService;
import pzinsta.pizzeria.service.impl.SignUpValidationServiceImpl;
import pzinsta.pizzeria.service.impl.UserServiceImpl;

public class SignUpHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserServiceImpl(new UserDaoImpl());
	private SignUpValidationService validationService = new SignUpValidationServiceImpl(new UserDaoImpl());

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		String phoneNumber = request.getParameter("phone-number");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm-password");

		Customer user = new Customer();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhoneNumber(phoneNumber);
		
		Map<String, String> validationErrors = validate(user, password, confirmPassword);
		if(!MapUtils.isEmpty(validationErrors)) {
			request.setAttribute("customer", user);
			request.setAttribute("validationErrors", validationErrors);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/sign-up");
			requestDispatcher.forward(request, response);
		}
		else {
			Customer customer = userService.createRegisteredCustomer(user, confirmPassword);
			request.getSession().setAttribute("customer", customer);
			response.sendRedirect(request.getContextPath());
		}
	}

	// TODO: refactor to a chain of responsibility
	private Map<String, String> validate(User user, String password, String confirmPassword) {
		Map<String, String> errors = new HashMap<>();
		if (StringUtils.isEmpty(user.getEmail())) {
			errors.put("email", "Email is required.");
		}
		
		if(StringUtils.isEmpty(user.getFirstName())) {
			errors.put("firstName", "First name is required.");
		}
		
		if(StringUtils.isEmpty(password)) {
			errors.put("password", "Password is required.");
		}
		
		if(StringUtils.isEmpty(confirmPassword)) {
			errors.put("confirmPassword", "Password confirmation is required.");
		}
		
		if(!StringUtils.equals(password, confirmPassword)) {
			errors.put("password", "Passwords do not match.");
		}
		
		if(!validationService.isEmailValid(user.getEmail())) {
			errors.put("email", "Email is already present.");
		}
		
		return errors;
	}

}
