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

import pzinsta.pizzeria.model.order.Order;
import pzinsta.pizzeria.model.order.OrderItem;
import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.IngredientType;
import pzinsta.pizzeria.model.pizza.Pizza;
import pzinsta.pizzeria.service.PizzaService;

public class PizzaBuilderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PizzaService pizzaService;

	@Override
	public void init() throws ServletException {
	    pizzaService = (PizzaService) getServletContext().getAttribute("pizzaService");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("bakeStyles", pizzaService.getBakeStyles());
		request.setAttribute("crusts", pizzaService.getCrusts());
		request.setAttribute("ingredientTypes", pizzaService.getIngredientTypes());
		request.setAttribute("cutStyles", pizzaService.getCutStyles());
		request.setAttribute("pizzaSizes", pizzaService.getPizzaSizes());

		Map<IngredientType, List<Ingredient>> ingredientsMap = pizzaService.getIngredients().stream()
				.collect(Collectors.groupingBy(i -> i.getType()));
		request.setAttribute("ingredientsMap", ingredientsMap);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pizzaBuilder.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long crustId = Long.parseLong(request.getParameter("crust"));
		long pizzaSizeId = Long.parseLong(request.getParameter("pizzaSize"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		long bakeStyleId = Long.parseLong(request.getParameter("bakeStyle"));
		long cutStyleId = Long.parseLong(request.getParameter("cutStyle"));
		
		Map<String, String[]> ingredientsParametersMap = Maps.filterKeys(request.getParameterMap(), key -> StringUtils.contains(key, "ingredient"));
		
		Pizza pizza = pizzaService.buildPizza(crustId, pizzaSizeId, quantity, bakeStyleId, cutStyleId, ingredientsParametersMap);
		
		Order order = Optional.ofNullable((Order) request.getSession().getAttribute("order")).orElseGet(Order::new);
		OrderItem orderItem = new OrderItem();
		orderItem.setPizza(pizza);
		orderItem.setQuantity(quantity);
		order.addOrderItem(orderItem);
		request.getSession().setAttribute("order", order);
		response.sendRedirect(request.getContextPath());
	}

}
