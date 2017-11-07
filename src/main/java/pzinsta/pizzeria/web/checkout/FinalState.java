package pzinsta.pizzeria.web.checkout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FinalState implements CheckoutState {

	private final static FinalState INSTANCE = new FinalState();

	private FinalState() {
	}

	@Override
	public String getView() {
		return "WEB-INF/checkout/finish.jsp";
	}

	@Override
	public CheckoutState handlePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return this;
	}
	
	@Override
	public CheckoutState handleGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("checkoutController");
		return this;
	}

	public static FinalState getInstance() {
		return INSTANCE;
	}

}
