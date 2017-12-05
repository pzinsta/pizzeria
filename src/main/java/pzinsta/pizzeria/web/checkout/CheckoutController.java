package pzinsta.pizzeria.web.checkout;

import java.io.IOException;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CheckoutController {
	private Deque<CheckoutState> previousCheckoutStatesStack;
	private CheckoutState currentState;

	public CheckoutController() {
		previousCheckoutStatesStack = new ConcurrentLinkedDeque<>();
		currentState = OrderNotConfirmedCheckoutState.getInstance();
	}

	public void handlePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (goBack(request) && currentState.isReturnToPreviousStateAllowed(request)) {
			currentState = previousCheckoutStatesStack.pop();
			forwardToView(request, response);
			return;
		}

		CheckoutState nextState = currentState.handlePost(request, response);
		if (nextState != currentState) {
			previousCheckoutStatesStack.push(currentState);
			currentState = nextState;
		}

		forwardToView(request, response);
	}

	private boolean goBack(HttpServletRequest request) {
		return StringUtils.equalsIgnoreCase(request.getParameter("action"), "back");
	}

	public void handleGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CheckoutState nextState = currentState.handleGet(request, response);
		if (nextState != currentState) {
			previousCheckoutStatesStack.push(currentState);
			currentState = nextState;
		}
		forwardToView(request, response);
	}

	private void forwardToView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(currentState.getView());
		requestDispatcher.forward(request, response);
	}

	public CheckoutState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(CheckoutState checkoutState) {
		this.currentState = checkoutState;
	}

}
