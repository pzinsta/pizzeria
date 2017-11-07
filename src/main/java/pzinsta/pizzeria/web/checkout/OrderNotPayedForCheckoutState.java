package pzinsta.pizzeria.web.checkout;

import static pzinsta.pizzeria.util.Utils.isRegisteredUser;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import pzinsta.pizzeria.dao.impl.OrderDaoImpl;
import pzinsta.pizzeria.dao.impl.PizzaDaoImpl;
import pzinsta.pizzeria.dao.impl.UserDaoImpl;
import pzinsta.pizzeria.model.Customer;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderStatus;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.PizzaService;
import pzinsta.pizzeria.service.UserService;
import pzinsta.pizzeria.service.impl.OrderServiceImpl;
import pzinsta.pizzeria.service.impl.PizzaServiceImpl;
import pzinsta.pizzeria.service.impl.UserServiceImpl;

public class OrderNotPayedForCheckoutState implements CheckoutState {

	private static final OrderNotPayedForCheckoutState INSTANCE = new OrderNotPayedForCheckoutState();
	private static final String PAGE_ID = "payment";
	private OrderService orderService;
	
	private OrderNotPayedForCheckoutState() {
		//TODO: move all this stuff to some initialization listener or something, idk
		UserService userService = new UserServiceImpl(new UserDaoImpl());
		PizzaService pizzaService = new PizzaServiceImpl(new PizzaDaoImpl());
		orderService = new OrderServiceImpl(new OrderDaoImpl(), userService, pizzaService);
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
		order.setStatus(OrderStatus.PAYED);
		
		Customer customer = (Customer) ObjectUtils.firstNonNull(request.getSession().getAttribute("customer"), request.getSession().getAttribute("unregisteredCustomer"));
		order.setCustomer(customer);
		
		orderService.saveOrder(order);
		
		request.getSession().removeAttribute("order");
		
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
