package pzinsta.pizzeria.model.order;

import pzinsta.pizzeria.model.pizza.Pizza;

public class OrderItem {
	private Pizza pizza;
	private int quantity;

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
