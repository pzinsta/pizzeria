package pzinsta.pizzeria.web.checkout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

interface CheckoutState {
	String getView();

	default CheckoutState handlePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return this;
	}

	default CheckoutState handleGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return this;
	}

	default boolean isReturnToPreviousStateAllowed(HttpServletRequest request) {
		return false;
	}
}
