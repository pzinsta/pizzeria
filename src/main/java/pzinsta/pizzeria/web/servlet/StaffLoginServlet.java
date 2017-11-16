package pzinsta.pizzeria.web.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import pzinsta.pizzeria.dao.impl.UserDaoImpl;
import pzinsta.pizzeria.model.User;
import pzinsta.pizzeria.service.UserService;
import pzinsta.pizzeria.service.impl.UserServiceImpl;

public class StaffLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserServiceImpl(new UserDaoImpl());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/staffLogin.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Optional<User> registeredStaffUserByEmailAndPassword = userService.getRegisteredStaffUserByEmailAndPassword(email, password);
		
		if (registeredStaffUserByEmailAndPassword.isPresent()) {
			request.getSession().setAttribute("staff-user", registeredStaffUserByEmailAndPassword.get());
			response.sendRedirect("dashboard");
		} else {
			response.sendRedirect(request.getRequestURI());
		}
	}

}
