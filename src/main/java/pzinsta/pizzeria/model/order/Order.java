package pzinsta.pizzeria.model.order;

import java.time.Instant;
import java.util.Collection;

import javax.money.MonetaryAmount;

import pzinsta.pizzeria.model.Customer;

public class Order {
	private long id;
	private Customer customer;
	private Collection<OrderItem> pizzas;
	private Instant placed;
	private OrderStatus status; // refactor to the State pattern?

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Instant getPlaced() {
		return placed;
	}

	public void setPlaced(Instant placedDate) {
		this.placed = placedDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus state) {
		this.status = state;
	}
	
	public MonetaryAmount getTotal() {
		return null;
	}

	public Collection<OrderItem> getPizzas() {
		return pizzas;
	}

	public void setPizzas(Collection<OrderItem> pizzas) {
		this.pizzas = pizzas;
	}

}
