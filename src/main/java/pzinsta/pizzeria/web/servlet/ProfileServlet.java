package pzinsta.pizzeria.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.service.UserService;

public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
	    userService = (UserService) getServletContext().getAttribute("userService");
	    super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.valueOf(request.getParameter("id"));
		String email = request.getParameter("email");
		String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		String phoneNumber = request.getParameter("phone-number");
		String address = request.getParameter("address");

		Customer customer = new Customer();
		customer.setId(id);
		customer.setEmail(email);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setPhoneNumber(phoneNumber);
		customer.setAddress(address);
		
		//TODO:validate
		
		userService.updateCustomer(customer);
		
		request.getSession().setAttribute("customer", customer);
		
		response.sendRedirect(request.getRequestURI());
	}

}
