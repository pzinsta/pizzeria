package pzinsta.pizzeria.web.checkout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FinalState implements CheckoutState {

	private final static FinalState INSTANCE = new FinalState();
	
	private FinalState() {
		// TODO Auto-generated constructor stub
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
	public CheckoutState next(HttpSession httpSession) {
		return this;
	}

	public static FinalState getInstance() {
		return INSTANCE;
	}

}
