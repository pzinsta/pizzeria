package pzinsta.pizzeria.web.checkout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import pzinsta.pizzeria.model.Customer;

public class CustomerInformationNotConfirmedCheckoutState implements CheckoutState {

	private final static CustomerInformationNotConfirmedCheckoutState INSTANCE = new CustomerInformationNotConfirmedCheckoutState();
	private static final String PAGE_ID = "customerInformationConfirmation";

	private CustomerInformationNotConfirmedCheckoutState() {
	}

	@Override
	public String getView() {
		return "WEB-INF/checkout/customerInformationConfirmation.jsp";
	}

	@Override
	public CheckoutState handlePost(HttpServletRequest request, HttpServletResponse response) {
		if (!isRequestComingFromValidPage(request)) {
			return this;
		}

		Customer customer = (Customer) ObjectUtils.firstNonNull(request.getSession().getAttribute("customer"), request.getSession().getAttribute("unregisteredCustomer"));
		customer.setFirstName(request.getParameter("first-name"));
		customer.setLastName(request.getParameter("last-name"));
		customer.setAddress(request.getParameter("address"));
		customer.setEmail(request.getParameter("email"));
		customer.setPhoneNumber(request.getParameter("phone-number"));
		
		return OrderNotPayedForCheckoutState.getInstance();
	}

	private boolean isRequestComingFromValidPage(HttpServletRequest request) {
		return StringUtils.equals(PAGE_ID, request.getParameter("page"));
	}

	public static CustomerInformationNotConfirmedCheckoutState getInstance() {
		return INSTANCE;
	}

}
