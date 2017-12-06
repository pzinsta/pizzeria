package pzinsta.pizzeria.web.checkout;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderStatus;
import pzinsta.pizzeria.service.OrderService;

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
		
		Order order = (Order) request.getSession().getAttribute("order");
		order.setPlaced(Instant.now());
		order.setStatus(OrderStatus.PAID);
		
		Customer customer = (Customer) ObjectUtils.firstNonNull(request.getSession().getAttribute("customer"), request.getSession().getAttribute("unregisteredCustomer"));
		order.setCustomer(customer);
		
		OrderService orderService = (OrderService) request.getServletContext().getAttribute("orderService");
		orderService.saveOrder(order);
		
		request.getSession().removeAttribute("order");
		
	    request.getSession().removeAttribute("checkoutController");
		
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
