package pzinsta.pizzeria.web.checkout;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import pzinsta.pizzeria.model.Customer;

class OrderConfirmedButCustomerNotLoggedInCheckoutState implements CheckoutState {

	private final static OrderConfirmedButCustomerNotLoggedInCheckoutState INSTANCE = new OrderConfirmedButCustomerNotLoggedInCheckoutState();
	
	private OrderConfirmedButCustomerNotLoggedInCheckoutState() {}
	
	@Override
	public String getView() {
		return "WEB-INF/checkout/logInOrOrderAsGuest.jsp";
	}

	@Override
	public CheckoutState next(HttpSession session) {
		return session.getAttribute("customer") == null ? this : CustomerInformationNotConfirmedCheckoutState.getInstance();
	}

	@Override
	public CheckoutState handlePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(StringUtils.equals(action, "order as a guest")) {
			Customer customer = new Customer();
			customer.setFirstName(request.getParameter("first-name"));
			customer.setLastName(request.getParameter("last-name"));
			customer.setAddress(request.getParameter("address"));
			customer.setEmail(request.getParameter("email"));
			request.getSession().setAttribute("customer", customer);
			return CustomerInformationNotConfirmedCheckoutState.getInstance();
		}
		return this; 
	}

	@Override
	public CheckoutState handleGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object customer = request.getSession().getAttribute("customer");
		if (customer == null) {
			return this;
		}
		return CustomerInformationNotConfirmedCheckoutState.getInstance();
	}
	
	public static OrderConfirmedButCustomerNotLoggedInCheckoutState getInstance() {
		return INSTANCE;
	}

}
