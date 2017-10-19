package pzinsta.pizzeria.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import pzinsta.pizzeria.dao.impl.PizzaDaoImpl;
import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderItem;
import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.IngredientType;
import pzinsta.pizzeria.model.pizza.Pizza;
import pzinsta.pizzeria.service.PizzaService;
import pzinsta.pizzeria.service.impl.PizzaServiceImpl;


public class EditOrderItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PizzaService pizzaService = new PizzaServiceImpl(new PizzaDaoImpl());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderItemId = request.getParameter("orderItemId");
		Optional<Order> orderOptional = Optional.ofNullable((Order)request.getSession().getAttribute("order"));
		Optional<OrderItem> orderItemOptional = orderOptional.flatMap((order) -> order.getOrderItemById(orderItemId));
		orderItemOptional.ifPresent(orderItem -> request.setAttribute("orderItem", orderItem));
		
		//TODO: refactor duplicate code here and in the PizzaBuilderServlet
		request.setAttribute("bakeStyles", pizzaService.getBakeStyles());
		request.setAttribute("crusts", pizzaService.getCrusts());
		request.setAttribute("ingredientTypes", pizzaService.getIngredientTypes());
		request.setAttribute("cutStyles", pizzaService.getCutStyles());
		request.setAttribute("pizzaSizes", pizzaService.getPizzaSizes());

		Map<IngredientType, List<Ingredient>> ingredientsMap = pizzaService.getIngredients().stream()
				.collect(Collectors.groupingBy(i -> i.getType()));
		request.setAttribute("ingredientsMap", ingredientsMap);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/editOrderItem.jsp");
		requestDispatcher.forward(request, response);
	}

	// TODO: copypaste
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long crustId = Long.parseLong(request.getParameter("crust"));
		long pizzaSizeId = Long.parseLong(request.getParameter("pizzaSize"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		long bakeStyleId = Long.parseLong(request.getParameter("bakeStyle"));
		long cutStyleId = Long.parseLong(request.getParameter("cutStyle"));
		String orderItemId = request.getParameter("orderItemId");
		
		Map<String, String[]> ingredientsParametersMap = Maps.filterKeys(request.getParameterMap(), key -> StringUtils.contains(key, "ingredient"));
		
		Pizza pizza = pizzaService.buildPizza(crustId, pizzaSizeId, quantity, bakeStyleId, cutStyleId, ingredientsParametersMap);
		
		Optional<OrderItem> orderItemOptional = Optional.ofNullable((Order) request.getSession().getAttribute("order")).flatMap(order -> order.getOrderItemById(orderItemId));
		orderItemOptional.ifPresent(orderItem -> {orderItem.setPizza(pizza); orderItem.setQuantity(quantity);});
		response.sendRedirect(request.getContextPath());
	}

}
