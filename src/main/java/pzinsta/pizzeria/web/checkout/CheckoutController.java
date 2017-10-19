package pzinsta.pizzeria.web.checkout;

import java.io.IOException;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		if (goBack(request)) {
			previousStep();
			redirect(request, response); //why redirect?
		} else {
			CheckoutState nextState = currentState.handlePost(request, response);
			if(nextState != currentState) {
				previousCheckoutStatesStack.push(currentState);
				currentState = nextState;
			}
			forwardToView(request, response);
		}
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getRequestURI());
	}

	private boolean goBack(HttpServletRequest request) {
		return StringUtils.equals(request.getParameter("action"), "back");
	}

	public void handleGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CheckoutState nextState = currentState.handleGet(request, response);
		if(nextState != currentState) {
			previousCheckoutStatesStack.push(currentState);
			currentState = nextState;
		}
		forwardToView(request, response);
	}

	private void previousStep() {
		currentState = previousCheckoutStatesStack.pop();
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
