package pzinsta.pizzeria.web.checkout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class OrderNotPayedForCheckoutState implements CheckoutState {

	private static final OrderNotPayedForCheckoutState INSTANCE = new OrderNotPayedForCheckoutState();
	private static final String PAGE_ID = "payment";

	private OrderNotPayedForCheckoutState() {
	}

	@Override
	public String getView() {
		return "WEB-INF/checkout/payment.jsp";
	}

	@Override
	public CheckoutState handlePost(HttpServletRequest request, HttpServletResponse response) {
		if (!isRequestComingFromValidPage(request)) {
			return this;
		}

		return FinalState.getInstance();
	}

	private boolean isRequestComingFromValidPage(HttpServletRequest request) {
		return StringUtils.equals(PAGE_ID, request.getParameter("page"));
	}

	public static OrderNotPayedForCheckoutState getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isReturnToPreviousStateAllowed(HttpServletRequest request) {
		return isRequestComingFromValidPage(request);
	}

}
