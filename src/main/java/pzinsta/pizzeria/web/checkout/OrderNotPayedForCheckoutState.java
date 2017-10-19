package pzinsta.pizzeria.web.checkout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderNotPayedForCheckoutState implements CheckoutState {

	private static final OrderNotPayedForCheckoutState INSTANCE = new OrderNotPayedForCheckoutState();
	
	private OrderNotPayedForCheckoutState() {}
	
	@Override
	public String getView() {
		return "WEB-INF/checkout/payment.jsp";
	}

	@Override
	public CheckoutState next(HttpSession httpSession) {
		return this;
	}

	@Override
	public CheckoutState handlePost(HttpServletRequest request, HttpServletResponse response) {
		return FinalState.getInstance();
	}

	public static OrderNotPayedForCheckoutState getInstance() {
		return INSTANCE;
	}

}
