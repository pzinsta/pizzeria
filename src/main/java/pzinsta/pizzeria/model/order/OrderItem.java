package pzinsta.pizzeria.model.order;

import java.util.UUID;

import javax.money.MonetaryAmount;

import pzinsta.pizzeria.model.pizza.Pizza;

public class OrderItem {
	private String id; //not to be persisted
	private Pizza pizza;
	private int quantity;

	public OrderItem() {
		id = UUID.randomUUID().toString();
	}
	
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

	public String getId() {
		return id;
	}

	public MonetaryAmount getCost() {
		return pizza.getCost();
	}
}
