package pzinsta.pizzeria.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pzinsta.pizzeria.web.checkout.CheckoutController;

public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CheckoutController checkoutController = retrieveCheckoutController(request);
		checkoutController.handleGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CheckoutController checkoutController = retrieveCheckoutController(request);
		checkoutController.handlePost(request, response);
	}

	private CheckoutController retrieveCheckoutController(HttpServletRequest request) {
		CheckoutController checkoutController = (CheckoutController) request.getSession()
				.getAttribute("checkoutController");
		if (checkoutController == null) {
			checkoutController = new CheckoutController();
			request.getSession().setAttribute("checkoutController", checkoutController);
		}
		return checkoutController;
	}
}
