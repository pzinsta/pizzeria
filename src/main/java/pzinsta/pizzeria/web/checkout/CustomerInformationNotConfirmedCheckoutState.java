package pzinsta.pizzeria.web.checkout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

public class CustomerInformationNotConfirmedCheckoutState implements CheckoutState {

	private final static CustomerInformationNotConfirmedCheckoutState INSTANCE = new CustomerInformationNotConfirmedCheckoutState();
	
	private CustomerInformationNotConfirmedCheckoutState() {}
	
	@Override
	public String getView() {
		return "WEB-INF/checkout/customerInformationConfirmation.jsp";
	}

	@Override
	public CheckoutState next(HttpSession httpSession) {
		return OrderNotPayedForCheckoutState.getInstance();
	}

	@Override
	public CheckoutState handlePost(HttpServletRequest request, HttpServletResponse response) {
		return OrderNotPayedForCheckoutState.getInstance();
	}

	public static CustomerInformationNotConfirmedCheckoutState getInstance() {
		return INSTANCE;
	}

}
