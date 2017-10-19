package pzinsta.pizzeria.web.checkout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class OrderNotConfirmedCheckoutState implements CheckoutState {

	private static final OrderNotConfirmedCheckoutState INSTANCE = new OrderNotConfirmedCheckoutState();
	
	private OrderNotConfirmedCheckoutState() {}
	
	@Override
	public String getView() {
		return "WEB-INF/checkout/confirm.jsp";
	}

	@Override
	public CheckoutState next(HttpSession httpSession) {
		if (httpSession.getAttribute("customer") != null) {
			return CustomerInformationNotConfirmedCheckoutState.getInstance();
		}
		else {
			return OrderConfirmedButCustomerNotLoggedInCheckoutState.getInstance();
		}
	}

	@Override
	public CheckoutState handlePost(HttpServletRequest request, HttpServletResponse response) {
		return OrderConfirmedButCustomerNotLoggedInCheckoutState.getInstance();
	}

	public static OrderNotConfirmedCheckoutState getInstance() {
		return INSTANCE;
	}

}
