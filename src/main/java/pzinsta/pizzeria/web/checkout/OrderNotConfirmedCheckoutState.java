package pzinsta.pizzeria.web.checkout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

class OrderNotConfirmedCheckoutState implements CheckoutState {

	private static final OrderNotConfirmedCheckoutState INSTANCE = new OrderNotConfirmedCheckoutState();
	private static final String PAGE_ID = "confirm";

	private OrderNotConfirmedCheckoutState() {
	}

	@Override
	public String getView() {
		return "WEB-INF/checkout/confirm.jsp";
	}

	@Override
	public CheckoutState handlePost(HttpServletRequest request, HttpServletResponse response) {
		if (!isRequestComingFromValidPage(request)) {
			return this;
		}
		
		if(request.getSession().getAttribute("customer") == null) {
			return OrderConfirmedButCustomerNotLoggedInCheckoutState.getInstance();
		}
		else {
			return CustomerInformationNotConfirmedCheckoutState.getInstance();
		}

	}

	private boolean isRequestComingFromValidPage(HttpServletRequest request) {
		return StringUtils.equals(PAGE_ID, request.getParameter("page"));
	}

	public static OrderNotConfirmedCheckoutState getInstance() {
		return INSTANCE;
	}

}
